package sergesv.rvs;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.ValidationUtil.MIN_PAGE_SIZE;

@Component
@ConfigurationProperties(prefix = "rvs")
@Validated
public class RvsPropertyResolver {
    private static final String PROPERTY_SORT_RESTAURANT = "rvs.sort-restaurant";
    private static final String PROPERTY_SORT_RESTAURANT_WITH_RATING =
            "rvs.sort-restaurant-with-rating";
    private static final String PROPERTY_SORT_MENU_ENTRY = "rvs.sort-menu-entry";
    private static final String PROPERTY_SORT_SINGLE_RESTAURANT_MENU_ENTRY =
            "rvs.sort-single-restaurant-menu-entry";
    private static final String PROPERTY_SORT_USER = "rvs.sort-user";
    private static final String PROPERTY_SORT_VOTE_ENTRY = "rvs.sort-vote-entry";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @NotNull
    @DateTimeFormat(iso = ISO.TIME)
    @Getter
    @Setter
    private LocalTime maxVoteTime;

    @Min(MIN_PAGE_SIZE)
    @Getter
    @Setter
    private int restaurantPageSize;

    @Min(MIN_PAGE_SIZE)
    @Getter
    @Setter
    private int menuEntryPageSize;

    @Min(MIN_PAGE_SIZE)
    @Getter
    @Setter
    private int userPageSize;

    @Min(MIN_PAGE_SIZE)
    @Getter
    @Setter
    private int voteEntryPageSize;

    @NotEmpty
    @Setter
    private String sortRestaurant;

    @NotEmpty
    @Setter
    private String sortRestaurantWithRating;

    @NotEmpty
    @Setter
    private String sortMenuEntry;

    @NotEmpty
    @Setter
    private String sortSingleRestaurantMenuEntry;

    @NotEmpty
    @Setter
    private String sortUser;

    @NotEmpty
    @Setter
    private String sortVoteEntry;

    @Getter
    private Sort restaurantSorter;

    @Getter
    private Sort restaurantWithRatingSorter;

    @Getter
    private Sort menuEntrySorter;

    @Getter
    private Sort singleRestaurantMenuEntrySorter;

    @Getter
    private Sort userSorter;

    @Getter
    private Sort voteEntrySorter;

    @PostConstruct
    private void init() {
        restaurantSorter = getDefaultSort(sortRestaurant, PROPERTY_SORT_RESTAURANT,
                RESTAURANT_PARAMS);
        restaurantWithRatingSorter = getDefaultSort(sortRestaurantWithRating,
                PROPERTY_SORT_RESTAURANT_WITH_RATING, RESTAURANT_WITH_RATING_PARAMS);
        menuEntrySorter = getDefaultSort(sortMenuEntry, PROPERTY_SORT_MENU_ENTRY,
                MENU_ENTRY_PARAMS);
        singleRestaurantMenuEntrySorter = getDefaultSort(sortSingleRestaurantMenuEntry,
                PROPERTY_SORT_SINGLE_RESTAURANT_MENU_ENTRY, SINGLE_RESTAURANT_MENU_ENTRY_PARAMS);
        userSorter = getDefaultSort(sortUser, PROPERTY_SORT_USER, USER_PARAMS);
        voteEntrySorter = getDefaultSort(sortVoteEntry, PROPERTY_SORT_VOTE_ENTRY,
                VOTE_ENTRY_PARAMS);
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
