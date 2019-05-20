package sergesv.rvs.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class SortUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String NICKNAME = "nickName";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String MENU_ENTRY_NAME = "menuEntry.name";
    private static final String MENU_ENTRY_PRICE = "menuEntry.price";
    private static final String RESTAURANT_NAME = "restaurant.name";
    private static final String DESC = "desc";
    private static final String ASC = "asc";
    private static final String RATING = "rating";
    private static final String SPLIT_REGEX = ",";

    public static final String[] RESTAURANT_PARAMS = {NAME, ASC, DESC};
    public static final String[] RESTAURANT_WITH_RATING_PARAMS = {NAME, RATING, ASC, DESC};
    public static final String[] MENU_ENTRY_PARAMS = {NAME, PRICE, DATE, ASC, DESC};
    public static final String[] SINGLE_RESTAURANT_MENU_ENTRY_PARAMS =
            {MENU_ENTRY_NAME, MENU_ENTRY_PRICE, ASC, DESC};
    public static final String[] USER_PARAMS =
            {ID, NICKNAME, EMAIL, FIRST_NAME, LAST_NAME, ASC, DESC};
    public static final String[] VOTE_ENTRY_PARAMS = {DATE, TIME, RESTAURANT_NAME, ASC, DESC};

    public static Optional<Sort> getSort(String sortParam, String... allowSortParams) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Optional.empty();
        }

        var params = sortParam.split(SPLIT_REGEX);
        var allowParamsList = Arrays.asList(allowSortParams);
        Sort sort = null;

        List<String> usedParams = new ArrayList<>(params.length);

        for (int i = 0; i < params.length; ++i) {
            var param = params[i];
            var nextParam = i == params.length - 1 ? "" : params[i + 1];

            if (usedParams.contains(param)) {
                throw new IllegalArgumentException(
                        String.format("Sort parameter %s is already used", param));
            }

            if (!allowParamsList.contains(param)) {
                throw new IllegalArgumentException(
                        String.format("%s is not correct sort parameter", param));
            }

            if (param.equals(ASC) || param.equals(DESC)) {
                throw new IllegalArgumentException(
                        String.format("%s must be placed after the sort parameter", param));
            }

            if (nextParam.equals(ASC)) {
                sort = compute(sort, param, Sort.Direction.ASC);
                ++i;
            } else if (nextParam.equals(DESC)) {
                sort = compute(sort, param, Sort.Direction.DESC);
                ++i;
            } else {
                sort = compute(sort, param, Sort.Direction.ASC);
            }

            usedParams.add(param);
        }

        return Optional.ofNullable(sort);
    }

    private static Sort compute(Sort sort, String param, Sort.Direction direction) {
        Sort resultSort = param.equals(RATING) ?
                JpaSort.unsafe(direction, "COUNT (voteEntry)") :
                Sort.by(direction, param);

        return sort == null ? resultSort : sort.and(resultSort);
    }

    private SortUtil() {
    }
}
