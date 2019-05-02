package sergesv.rvs.util.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public final class ControllerUtil {
    public enum ParamsCondition {
        DEFAULT,
        BY_DATE,
        BETWEEN_DATES,
        MENU,
        RATING,
        RATING_BY_DATE,
        RATING_BETWEEN_DATES,
        MENU_AND_RATING,
        MENU_AND_RATING_BY_DATE,
        MENU_AND_RATING_BETWEEN_DATES
    }

    public static ParamsCondition resolveParams(Boolean rating, Boolean menu, LocalDate ratingDate,
                                          LocalDate ratingDateStart, LocalDate ratingDateEnd) {
        boolean ratingOption = Optional.ofNullable(rating).orElse(false);
        boolean menuOption = Optional.ofNullable(menu).orElse(false);

        if (!ratingOption && !menuOption) {
            return ParamsCondition.DEFAULT;
        } else if (!menuOption) {
            switch (resolveParams(ratingDate, ratingDateStart, ratingDateEnd)) {
                case BY_DATE:
                    return ParamsCondition.RATING_BY_DATE;
                case BETWEEN_DATES:
                    return ParamsCondition.RATING_BETWEEN_DATES;
                default:
                    return ParamsCondition.RATING;
            }
        } else if (!ratingOption) {
            return ParamsCondition.MENU;
        } else {
            switch (resolveParams(ratingDate, ratingDateStart, ratingDateEnd)) {
                case BY_DATE:
                    return ParamsCondition.MENU_AND_RATING_BY_DATE;
                case BETWEEN_DATES:
                    return ParamsCondition.MENU_AND_RATING_BETWEEN_DATES;
                default:
                    return ParamsCondition.MENU_AND_RATING;
            }
        }
    }

    public static ParamsCondition resolveParams(LocalDate date, LocalDate dateStart,
                                          LocalDate dateEnd) {
        if (date == null && dateStart == null && dateEnd == null) {
            return ParamsCondition.DEFAULT;
        } else if (dateStart != null || dateEnd != null) {
            return ParamsCondition.BETWEEN_DATES;
        } else {
            return ParamsCondition.BY_DATE;
        }
    }

    public static ParamsCondition resolveParams(LocalDate dateStart, LocalDate dateEnd) {
        return resolveParams(null, dateStart, dateEnd);
    }

    public static Pageable getPageable(Integer page, Integer size, int defaultPageSize) {
        var pageOptional = Optional.ofNullable(page);
        var sizeOptional = Optional.ofNullable(size);

        if (pageOptional.isEmpty() && sizeOptional.isEmpty()) {
            return Pageable.unpaged();
        }

        return PageRequest.of(pageOptional.orElse(0), sizeOptional.orElse(defaultPageSize));
    }

    private ControllerUtil() {
    }
}
