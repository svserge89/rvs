package sergesv.rvs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import sergesv.rvs.validator.annotation.SortConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.ValidationUtil.MIN_PAGE_SIZE;

@Component
@ConfigurationProperties(prefix = "rvs")
@Validated
@Getter
@Setter
public class RvsPropertyResolver {
    @NotNull
    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime maxVoteTime;

    @Min(MIN_PAGE_SIZE)
    private int restaurantPageSize;

    @Min(MIN_PAGE_SIZE)
    private int menuEntryPageSize;

    @Min(MIN_PAGE_SIZE)
    private int userPageSize;

    @Min(MIN_PAGE_SIZE)
    private int voteEntryPageSize;

    @NotNull
    @SortConstraint(allowedParams = NAME)
    private Sort sortRestaurant;

    @NotNull
    @SortConstraint(allowedParams = {NAME, RATING})
    private Sort sortRestaurantWithRating;

    @NotNull
    @SortConstraint(allowedParams = {NAME, PRICE, DATE})
    private Sort sortMenuEntry;

    @NotNull
    @SortConstraint(allowedParams = {MENU_ENTRY_NAME, MENU_ENTRY_PRICE})
    private Sort sortSingleRestaurantMenuEntry;

    @NotNull
    @SortConstraint(allowedParams = {ID, NICKNAME, EMAIL, FIRST_NAME, LAST_NAME})
    private Sort sortUser;

    @NotNull
    @SortConstraint(allowedParams = {DATE, TIME, RESTAURANT_NAME})
    private Sort sortVoteEntry;
}
