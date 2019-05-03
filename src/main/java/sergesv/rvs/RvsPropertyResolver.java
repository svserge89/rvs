package sergesv.rvs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;

@Component
@ConfigurationProperties(prefix = "rvs")
@Setter
public class RvsPropertyResolver {
    private static final int DEFAULT_RESTAURANT_PAGE_SIZE = 10;
    private static final int DEFAULT_MENU_ENTRY_PAGE_SIZE = 10;
    private static final int DEFAULT_USER_PAGE_SIZE = 10;
    private static final int DEFAULT_VOTE_ENTRY_PAGE_SIZE = 10;
    private static final LocalTime DEFAULT_MAX_VOTE_TIME = LocalTime.of(11, 0);

    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime maxVoteTime;

    private int restaurantPageSize;

    private int menuEntryPageSize;

    private int userPageSize;

    private int voteEntryPageSize;

    @Getter
    private String sortRestaurant;

    @Getter
    private String sortRestaurantWithMenu;

    @Getter
    private String sortMenuEntry;

    @Getter
    private String sortUser;

    @Getter
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
}
