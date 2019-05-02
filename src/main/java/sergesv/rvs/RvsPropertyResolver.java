package sergesv.rvs;

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
    private static final int DEFAULT_RESTAURANT_PAGE_SIZE = 20;
    private static final LocalTime DEFAULT_MAX_VOTE_TIME = LocalTime.of(11, 0);

    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime maxVoteTime;

    private int restaurantPageSize;

    public LocalTime getMaxVoteTime() {
        return Optional.ofNullable(maxVoteTime).orElse(DEFAULT_MAX_VOTE_TIME);
    }

    public int getRestaurantPageSize() {
        return restaurantPageSize == 0 ? DEFAULT_RESTAURANT_PAGE_SIZE : restaurantPageSize;
    }
}
