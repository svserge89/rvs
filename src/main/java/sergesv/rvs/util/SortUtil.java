package sergesv.rvs.util;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

public final class SortUtil {
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NICKNAME = "nickName";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String DESC = "desc";

    private static final String SPLIT_REGEX = ",";

    public static Optional<Sort> getSort(String sortParam, String... allowSortParams) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Optional.empty();
        }

        var params = sortParam.split(SPLIT_REGEX);
        var allowParamsList = Arrays.asList(allowSortParams);
        Sort sort = null;

        for (var param : params) {
            if (!allowParamsList.contains(param)) {
                throw new IllegalArgumentException(
                        String.format("%s is not correct sort parameter", param));
            }

            if (param.equals(DESC)) {
                sort = computeDesc(sort);
            } else {
                sort = compute(sort, param);
            }
        }

        return Optional.ofNullable(sort);
    }

    private static Sort compute(Sort sort, String param) {
        return sort == null ? Sort.by(param) : sort.and(Sort.by(param));
    }

    private static Sort computeDesc(Sort sort) {
        if (sort == null) {
            throw new IllegalArgumentException("desc can not be first sort parameter");
        }

        return sort.descending();
    }

    private SortUtil() {
    }
}
