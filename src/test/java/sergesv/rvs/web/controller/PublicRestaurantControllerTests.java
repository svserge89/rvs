package sergesv.rvs.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.util.TestUtil.MenuEntryPageTo;
import sergesv.rvs.util.TestUtil.RestaurantPageTo;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.checkGetNotFound;
import static sergesv.rvs.util.TestUtil.checkGet;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class PublicRestaurantControllerTests extends AbstractControllerTests {
    @Test
    void getAll() {
        checkGet(restTemplate, "/api/public/restaurants", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(), TOTAL_PAGES,
                        RESTAURANT_TOS[FIRST], RESTAURANT_TOS[SECOND], RESTAURANT_TOS[THIRD]));
    }

    @Test
    void getAllWithRating() {
        checkGet(restTemplate, "/api/public/restaurants?rating=true", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_CURRENT_RATING[FIRST],
                        RESTAURANT_TOS_WITH_CURRENT_RATING[SECOND],
                        RESTAURANT_TOS_WITH_CURRENT_RATING[THIRD]));
    }

    @Test
    void getAllWithRatingSortedByRatingAscNameDesc() {
        checkGet(restTemplate,
                "/api/public/restaurants?rating=true&sort=rating,name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_CURRENT_RATING[THIRD],
                        RESTAURANT_TOS_WITH_CURRENT_RATING[SECOND],
                        RESTAURANT_TOS_WITH_CURRENT_RATING[FIRST]));
    }

    @Test
    void getAllWithRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants?rating=true&date=" + PREV_1D_DATE,
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_PREV_1D_RATING[THIRD],
                        RESTAURANT_TOS_WITH_PREV_1D_RATING[FIRST],
                        RESTAURANT_TOS_WITH_PREV_1D_RATING[SECOND]));
    }

    @Test
    void getOne() {
        checkGet(restTemplate, "/api/public/restaurants/1", RestaurantTo.class,
                RESTAURANT_TOS[FIRST]);
    }

    @Test
    void getOneNotFound() {
        checkGetNotFound(restTemplate, "/api/public/restaurants/10");
    }

    @Test
    void getOneWithMenu() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true", RestaurantTo.class,
                RESTAURANT_TO_WITH_MENU);
    }

    @Test
    void getOneWithMenuSortedByNameDesc() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&sort=menuEntry.name,desc",
                RestaurantTo.class, RESTAURANT_TO_WITH_MENU_DESC);
    }

    @Test
    void getOneWithRating() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true", RestaurantTo.class,
                RESTAURANT_TO_WITH_CURRENT_RATING);
    }

    @Test
    void getOneWithRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true&date=" + PREV_1D_DATE,
                RestaurantTo.class, RESTAURANT_TO_WITH_PREV_1D_RATING);
    }

    @Test
    void getOneWithMenuAndRating() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true",
                RestaurantTo.class, RESTAURANT_TO_WITH_CURRENT_MENU_AND_RATING);
    }

    @Test
    void getOneWithMenuAndRatingSortedByNameDesc() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&" +
                        "sort=menuEntry.name,desc", RestaurantTo.class,
                RESTAURANT_TO_WITH_CURRENT_MENU_DESC_AND_RATING);
    }

    @Test
    void getOneWithMenuAndRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&date=" +
                        PREV_1D_DATE, RestaurantTo.class,
                RESTAURANT_TO_WITH_PREV_1D_MENU_AND_RATING);
    }

    @Test
    void getMenu() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu", MenuEntryPageTo.class,
                buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        CURRENT_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[THIRD],
                        PREV_2D_MENU_ENTRY_TOS[SECOND], PREV_2D_MENU_ENTRY_TOS[FIRST],
                        PREV_2D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuSortedByNameDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu?sort=name,date",
                MenuEntryPageTo.class, buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(),
                        TOTAL_PAGES, PREV_2D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[FIRST], PREV_2D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[SECOND],
                        PREV_2D_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[THIRD],
                        CURRENT_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu?date=" + PREV_1D_DATE,
                MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByStartDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu?dateStart=" + PREV_1D_DATE,
                MenuEntryPageTo.class, buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(),
                        TOTAL_PAGES, CURRENT_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByEndDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu?dateEnd=" + PREV_1D_DATE,
                MenuEntryPageTo.class, buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(),
                        TOTAL_PAGES, PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD], PREV_2D_MENU_ENTRY_TOS[SECOND],
                        PREV_2D_MENU_ENTRY_TOS[FIRST], PREV_2D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByDateBetween() {
        checkGet(restTemplate, String.format("/api/public/restaurants/1/menu?dateStart=%s&" +
                        "dateEnd=%s", PREV_1D_DATE, PREV_1D_DATE), MenuEntryPageTo.class,
                buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuEntry() {
        checkGet(restTemplate, "/api/public/restaurants/1/menu/311", MenuEntryTo.class,
                CURRENT_MENU_ENTRY_TOS[FIRST]);
    }

    @Test
    void getMenuEntryNotFound() {
        checkGetNotFound(restTemplate, "/api/public/restaurants/1/menu/321");
    }

    @Test
    void getRating() {
        checkGet(restTemplate, "/api/public/restaurants/1/rating", Integer.class,
                CURRENT_RATING[FIRST]);
    }

    @Test
    void getRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/rating?date=" + PREV_1D_DATE,
                Integer.class, PREV_1D_RATING[FIRST]);
    }
}
