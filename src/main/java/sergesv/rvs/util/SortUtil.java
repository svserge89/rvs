package sergesv.rvs.util;

import org.springframework.data.domain.Sort;

import java.util.Optional;

public final class SortUtil {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NICKNAME = "nickName";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MENU_ENTRY_NAME = "menuEntry.name";
    public static final String MENU_ENTRY_PRICE = "menuEntry.price";
    public static final String RESTAURANT_NAME = "restaurant.name";
    public static final String RATING = "rating";

    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private static final String SPLIT_REGEX = ",";

    public static Optional<Sort> getSort(String sortParam) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Optional.empty();
        }

        var params = sortParam.split(SPLIT_REGEX);
        Sort sort = null;

        for (int i = 0; i < params.length; ++i) {
            var param = params[i];
            var nextParam = i == params.length - 1 ? "" : params[i + 1];

            if (param.equals(ASC) || param.equals(DESC)) {
                throw new IllegalArgumentException(
                        String.format("%s must be placed after the sort parameter", param));
            }

            var direction = Sort.Direction.ASC;

            switch (nextParam) {
                case DESC:  direction = Sort.Direction.DESC;
                case ASC:   ++i;
            }

            Sort resultSort = Sort.by(direction, param);

            sort = sort == null ? resultSort : sort.and(resultSort);
        }

        return Optional.ofNullable(sort);
    }

    private SortUtil() {
    }
}
