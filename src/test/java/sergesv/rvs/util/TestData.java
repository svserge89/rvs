package sergesv.rvs.util;

import sergesv.rvs.web.to.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ValidationUtil.PRICE_FRACTION;

public final class TestData {
    public static final LocalDate CURRENT_DATE = LocalDate.now();
    public static final LocalDate PREV_1D_DATE = CURRENT_DATE.minus(Period.ofDays(1));

    private static final LocalDate PREV_2D_DATE = CURRENT_DATE.minus(Period.ofDays(2));

    public static final int PAGE = 0;
    public static final int TOTAL_PAGES = 1;
    public static final int TOTAL_EMPTY_PAGE = 0;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private static final BigDecimal[] PRICE = {
            new BigDecimal(50.0).setScale(PRICE_FRACTION, RoundingMode.HALF_UP),
            new BigDecimal(10.5).setScale(PRICE_FRACTION, RoundingMode.HALF_UP),
            new BigDecimal(50.0).setScale(PRICE_FRACTION, RoundingMode.HALF_UP)
    };

    public static final int[] CURRENT_RATING = {1, 1, 0};
    public static final int[] PREV_1D_RATING = {0, 0, 2};

    private static final int COUNT_RESTAURANTS = 3;
    private static final int FIRST_ID = 1;
    private static final int CURRENT_MENU_FIRST_ID = 311;
    private static final int PREV_1D_MENU_FIRST_ID = 211;
    private static final int PREV_2D_MENU_FIRST_ID = 111;

    private static Comparator<MenuEntryTo> menuPriceComparator =
            Comparator.comparing(MenuEntryTo::getPrice).thenComparing(MenuEntryTo::getName);
    private static Comparator<MenuEntryTo> menuNameDescComparator =
            Comparator.comparing(MenuEntryTo::getName).reversed();

    public static final RestaurantTo NEW_RESTAURANT_TO =
            new RestaurantTo(0, "New Restaurant", null, null);

    public static final RestaurantTo[] RESTAURANT_TOS =
            buildRestaurantTos(false, CURRENT_RATING);
    public static final RestaurantTo[] RESTAURANT_TOS_WITH_CURRENT_RATING =
            buildRestaurantTos(true, CURRENT_RATING);
    public static final RestaurantTo[] RESTAURANT_TOS_WITH_PREV_1D_RATING =
            buildRestaurantTos(true, PREV_1D_RATING);

    public static final RestaurantTo RESTAURANT_TO_WITH_MENU =
            buildRestaurantTo(FIRST_ID, buildMenuEntryToSet(buildMenuEntryTos(
                    CURRENT_MENU_FIRST_ID, FIRST_ID, CURRENT_DATE), menuPriceComparator),
                    null);
    public static final RestaurantTo RESTAURANT_TO_WITH_MENU_DESC =
            buildRestaurantTo(FIRST_ID, buildMenuEntryToSet(buildMenuEntryTos(
                    CURRENT_MENU_FIRST_ID, FIRST_ID, CURRENT_DATE), menuNameDescComparator),
                    null);
    public static final RestaurantTo RESTAURANT_TO_WITH_CURRENT_RATING =
            buildRestaurantTo(FIRST_ID, null, CURRENT_RATING[FIRST]);
    public static final RestaurantTo RESTAURANT_TO_WITH_PREV_1D_RATING =
            buildRestaurantTo(FIRST_ID, null, PREV_1D_RATING[FIRST]);
    public static final RestaurantTo RESTAURANT_TO_WITH_CURRENT_MENU_AND_RATING =
            buildRestaurantTo(FIRST_ID, buildMenuEntryToSet(buildMenuEntryTos(
                    CURRENT_MENU_FIRST_ID, FIRST_ID, CURRENT_DATE), menuPriceComparator),
                    CURRENT_RATING[FIRST]);
    public static final RestaurantTo RESTAURANT_TO_WITH_CURRENT_MENU_DESC_AND_RATING =
            buildRestaurantTo(FIRST_ID, buildMenuEntryToSet(buildMenuEntryTos(
                    CURRENT_MENU_FIRST_ID, FIRST_ID, CURRENT_DATE), menuNameDescComparator),
                    CURRENT_RATING[FIRST]);
    public static final RestaurantTo RESTAURANT_TO_WITH_PREV_1D_MENU_AND_RATING =
            buildRestaurantTo(FIRST_ID, buildMenuEntryToSet(buildMenuEntryTos(
                    PREV_1D_MENU_FIRST_ID, FIRST_ID, PREV_1D_DATE), menuPriceComparator),
                    PREV_1D_RATING[FIRST]);

    public static final MenuEntryTo NEW_MENU_ENTRY_TO =
            new MenuEntryTo(0, "New Menu Entry", new BigDecimal(20.5)
                    .setScale(PRICE_FRACTION, RoundingMode.HALF_UP), CURRENT_DATE);

    public static final MenuEntryTo[] CURRENT_MENU_ENTRY_TOS =
            buildMenuEntryTos(CURRENT_MENU_FIRST_ID, FIRST_ID, CURRENT_DATE);
    public static final MenuEntryTo[] PREV_1D_MENU_ENTRY_TOS =
            buildMenuEntryTos(PREV_1D_MENU_FIRST_ID, FIRST_ID, PREV_1D_DATE);
    public static final MenuEntryTo[] PREV_2D_MENU_ENTRY_TOS =
            buildMenuEntryTos(PREV_2D_MENU_FIRST_ID, FIRST_ID, PREV_2D_DATE);

    public static final UserTo NEW_USER_TO = new UserTo(0, "new_user",
            "new_first_name", "new_last_name", "newUser@mail.com",
            "password", false, true);

    public static final UserTo ADMIN = new UserTo(FIRST_ID, "admin", null,
            null, "admin@mail.com", "password", true, true);
    public static final UserTo USER_1 = new UserTo(FIRST_ID + 1, "user_1",
            "first_name_1", "last_name_1", "user1@mail.com",
            "password", false, true);
    public static final UserTo USER_2 = new UserTo(FIRST_ID + 2, "user_2",
            "first_name_2", "last_name_2", "user2@mail.com",
            "password", false, true);

    public static final VoteEntryTo[] USER_1_VOTE_ENTRY_TOS = {
            new VoteEntryTo(RESTAURANT_TOS[FIRST], LocalDateTime.of(PREV_2D_DATE,
                    LocalTime.of(15, 30))),
            new VoteEntryTo(RESTAURANT_TOS[THIRD], LocalDateTime.of(PREV_1D_DATE,
                    LocalTime.of(12, 0))),
            new VoteEntryTo(RESTAURANT_TOS[SECOND], LocalDateTime.of(CURRENT_DATE,
                    LocalTime.of(9, 0)))
    };

    private static RestaurantTo buildRestaurantTo(long id, LinkedHashSet<MenuEntryTo> menu,
                                                  Integer rating) {
        return new RestaurantTo(id, "restaurant_" + id, menu, rating);
    }

    private static MenuEntryTo buildMenuEntryTo(long id, long restaurantId, BigDecimal price,
                                                LocalDate date) {
        return new MenuEntryTo(id, String.format("r%d_menu_entry_%d", restaurantId, id % 10), price,
                date);
    }

    private static MenuEntryTo[] buildMenuEntryTos(long startId, long restaurantId,
                                                   LocalDate date) {
        var result = new MenuEntryTo[PRICE.length];

        for (int i = 0; i < PRICE.length; ++i) {
            result[i] = buildMenuEntryTo(startId + i, restaurantId, PRICE[i], date);
        }

        return result;
    }

    private static LinkedHashSet<MenuEntryTo> buildMenuEntryToSet(MenuEntryTo[] menuEntryTos,
                                                                  Comparator<MenuEntryTo>
                                                                  comparator) {
        return Arrays.stream(menuEntryTos)
                .sorted(comparator).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static RestaurantTo[] buildRestaurantTos(boolean rating, int[] ratings) {
        var result = new RestaurantTo[COUNT_RESTAURANTS];

        for (int i = 0; i < result.length; ++i) {
            Integer ratingValue = null;

            if (rating) {
                ratingValue = ratings[i];
            }

            result[i] = buildRestaurantTo(i + 1, null, ratingValue);
        }

        return result;
    }

    public static <T> PageTo<T> buildPage(int page, int size, int total, T ...objects) {
        return new PageTo<>(List.of(objects), page, size, total);
    }

    private TestData() {
    }
}
