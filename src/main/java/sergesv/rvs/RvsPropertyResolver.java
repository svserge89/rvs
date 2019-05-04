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

    private String sortRestaurantWithMenu;

    private String sortMenuEntry;

    private String sortUser;

    private String sortVoteEntry;

    public LocalTime getMaxVoteTime() {
        return Optional.ofNullable(maxVoteTime).orElse(DEFAULT_MAX_VOTE_TIME);
    }

    public int getRestaurantPageSize() {
        return restaurantPageSize == 0 ? DEFAULT_RESTAURANT_PAGE_SIZE : restaurantPageSize;
    }

    public int getMenuEntryPageSize() {
        return menuEntryPageSize == 0 ? DEFAULT_MENU_ENTRY_PAGE_SIZE : menuEntryPageSize;
    }

    public int getUserPageSize() {
        return userPageSize == 0 ? DEFAULT_USER_PAGE_SIZE : userPageSize;
    }

    public int getVoteEntryPageSize() {
        return voteEntryPageSize == 0 ? DEFAULT_VOTE_ENTRY_PAGE_SIZE : voteEntryPageSize;
    }

    public Sort getSortRestaurant() {
        return getDefaultSort(sortRestaurant, RESTAURANT_PARAMS);
    }

    public Sort getSortRestaurantWithMenu() {
        return getDefaultSort(sortRestaurantWithMenu, RESTAURANT_WITH_MENU_PARAMS);
    }

    public Sort getSortMenuEntry() {
        return getDefaultSort(sortMenuEntry, MENU_ENTRY_PARAMS);
    }

    public Sort getSortUser() {
        return getDefaultSort(sortUser, USER_PARAMS);
    }

    public Sort getSortVoteEntry() {
        return getDefaultSort(sortVoteEntry, VOTE_ENTRY_PARAMS);
    }

    private Sort getDefaultSort(String sortProperty, String... allowedSortParams) {
        try {
            return getSort(sortProperty, allowedSortParams).orElse(Sort.unsorted());
        } catch (IllegalArgumentException exception) {
            log.warn("{} is incorrect sort property", sortRestaurant, exception);

            return Sort.unsorted();
        }
    }
}
