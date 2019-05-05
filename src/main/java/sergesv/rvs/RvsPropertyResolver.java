package sergesv.rvs;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.SortUtil.*;

@Component
@ConfigurationProperties(prefix = "rvs")
@Setter
public class RvsPropertyResolver {
    private static final String PROPERTY_RESTAURANT_PAGE_SIZE = "rvs.restaurant-page-size";
    private static final String PROPERTY_MENU_ENTRY_PAGE_SIZE = "rvs.menu-entry-page-size";
    private static final String PROPERTY_USER_PAGE_SIZE = "rvs.user-page-size";
    private static final String PROPERTY_VOTE_ENTRY_PAGE_SIZE = "rvs.vote-entry-page-size";
    private static final String PROPERTY_SORT_RESTAURANT = "rvs.sort-restaurant";
    private static final String PROPERTY_SORT_RESTAURANT_WITH_RATING =
            "rvs.sort-restaurant-with-rating";
    private static final String PROPERTY_SORT_RESTAURANT_WITH_MENU =
            "rvs.sort-restaurant-with-menu";
    private static final String PROPERTY_SORT_RESTAURANT_WITH_MENU_AND_RATING =
            "rvs.sort-restaurant-with-menu-and-rating";
    private static final String PROPERTY_SORT_MENU_ENTRY = "rvs.sort-menu-entry";
    private static final String PROPERTY_SORT_USER = "rvs.sort-user";
    private static final String PROPERTY_SORT_VOTE_ENTRY = "rvs.sort-vote-entry";

    private static final int DEFAULT_RESTAURANT_PAGE_SIZE = 10;
    private static final int DEFAULT_MENU_ENTRY_PAGE_SIZE = 10;
    private static final int DEFAULT_USER_PAGE_SIZE = 10;
    private static final int DEFAULT_VOTE_ENTRY_PAGE_SIZE = 10;
    private static final LocalTime DEFAULT_MAX_VOTE_TIME = LocalTime.of(11, 0);

    private static final Logger log = LoggerFactory.getLogger(RvsPropertyResolver.class);

    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime maxVoteTime;

    private int restaurantPageSize;

    private int menuEntryPageSize;

    private int userPageSize;

    private int voteEntryPageSize;

    private String sortRestaurant;

    private String sortRestaurantWithRating;

    private String sortRestaurantWithMenu;

    private String sortRestaurantWithMenuAndRating;

    private String sortMenuEntry;

    private String sortUser;

    private String sortVoteEntry;

    public LocalTime getMaxVoteTime() {
        var optional = Optional.ofNullable(maxVoteTime);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            log.warn("Property rvs.max-vote-time has incorrect value, using default value {}",
                    DEFAULT_MAX_VOTE_TIME);

            return DEFAULT_MAX_VOTE_TIME;
        }
    }

    public int getRestaurantPageSize() {
        return getDefaultPageSize(restaurantPageSize, DEFAULT_RESTAURANT_PAGE_SIZE,
                PROPERTY_RESTAURANT_PAGE_SIZE);
    }

    public int getMenuEntryPageSize() {
        return getDefaultPageSize(menuEntryPageSize, DEFAULT_MENU_ENTRY_PAGE_SIZE,
                PROPERTY_MENU_ENTRY_PAGE_SIZE);
    }

    public int getUserPageSize() {
        return getDefaultPageSize(userPageSize, DEFAULT_USER_PAGE_SIZE, PROPERTY_USER_PAGE_SIZE);
    }

    public int getVoteEntryPageSize() {
        return getDefaultPageSize(voteEntryPageSize, DEFAULT_VOTE_ENTRY_PAGE_SIZE,
                PROPERTY_VOTE_ENTRY_PAGE_SIZE);
    }

    public Sort getSortRestaurant() {
        return getDefaultSort(sortRestaurant, PROPERTY_SORT_RESTAURANT, RESTAURANT_PARAMS);
    }

    public Sort getSortRestaurantWithRating() {
        return getDefaultSort(sortRestaurantWithRating, PROPERTY_SORT_RESTAURANT_WITH_RATING,
                RESTAURANT_WITH_RATING_PARAMS);
    }

    public Sort getSortRestaurantWithMenu() {
        return getDefaultSort(sortRestaurantWithMenu, PROPERTY_SORT_RESTAURANT_WITH_MENU,
                RESTAURANT_WITH_MENU_PARAMS);
    }

    public Sort getSortRestaurantWithMenuAndRating() {
        return getDefaultSort(sortRestaurantWithMenuAndRating,
                PROPERTY_SORT_RESTAURANT_WITH_MENU_AND_RATING,
                RESTAURANT_WITH_MENU_AND_RATING_PARAMS);
    }

    public Sort getSortMenuEntry() {
        return getDefaultSort(sortMenuEntry, PROPERTY_SORT_MENU_ENTRY, MENU_ENTRY_PARAMS);
    }

    public Sort getSortUser() {
        return getDefaultSort(sortUser, PROPERTY_SORT_USER, USER_PARAMS);
    }

    public Sort getSortVoteEntry() {
        return getDefaultSort(sortVoteEntry, PROPERTY_SORT_VOTE_ENTRY, VOTE_ENTRY_PARAMS);
    }

    private int getDefaultPageSize(int value, int defaultValue, String propertyName) {
        if (value > 0) {
            return value;
        } else {
            log.warn("Property {} has incorrect value, using default value {}", propertyName,
                    defaultValue);

            return defaultValue;
        }
    }

    private Sort getDefaultSort(String sortValue, String propertyName,
                                String... allowedSortParams) {
        try {
            return getSort(sortValue, allowedSortParams).orElse(Sort.unsorted());
        } catch (IllegalArgumentException exception) {
            log.warn("Property {} has incorrect value, sorting disabled", propertyName, exception);

            return Sort.unsorted();
        }
    }
}
