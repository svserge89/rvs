package sergesv.rvs.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.util.TestUtil.MenuEntryPageTo;
import sergesv.rvs.util.TestUtil.RestaurantPageTo;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.checkGetNotFound;
import static sergesv.rvs.util.TestUtil.checkGet;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = BEFORE_CLASS)
class PublicRestaurantControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RvsPropertyResolver propertyResolver;

    @Test
    void getAll() {
        checkGet(restTemplate, "/api/public/restaurants", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(), TOTAL_PAGES,
                        RESTAURANT_TOS[FIRST], RESTAURANT_TOS[SECOND], RESTAURANT_TOS[THIRD]));
    }

    @Test
    void getAllWithMenu() {
        checkGet(restTemplate, "/api/public/restaurants?menu=true", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU[FIRST],
                        RESTAURANT_TOS_WITH_MENU[SECOND], RESTAURANT_TOS_WITH_MENU[THIRD]));
    }

    @Test
    void getAllWithMenuSortedByNameDescMenuEntryNameDesc() {
        checkGet(restTemplate,
                "/api/public/restaurants?menu=true&sort=name,desc,menuEntry.name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_DESC[THIRD],
                        RESTAURANT_TOS_WITH_MENU_DESC[SECOND],
                        RESTAURANT_TOS_WITH_MENU_DESC[FIRST]));
    }

    @Test
    void getAllWithMenuByDate() {
        checkGet(restTemplate, "/api/public/restaurants?menu=true&menuDate=" + PREV_1D_DATE,
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[FIRST],
                        RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[SECOND],
                        RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[THIRD]));
    }

    @Test
    void getAllWithRating() {
        checkGet(restTemplate, "/api/public/restaurants?rating=true", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_RATING[FIRST],
                        RESTAURANT_TOS_WITH_RATING[SECOND], RESTAURANT_TOS_WITH_RATING[THIRD]));
    }

    @Test
    void getAllWithRatingSortedByRatingAscNameDesc() {
        checkGet(restTemplate,
                "/api/public/restaurants?rating=true&sort=rating,name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_RATING[THIRD],
                        RESTAURANT_TOS_WITH_RATING[SECOND], RESTAURANT_TOS_WITH_RATING[FIRST]));
    }

    @Test
    void getAllWithMenuAndRating() {
        checkGet(restTemplate, "/api/public/restaurants?rating=true&menu=true",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_AND_RATING[FIRST],
                        RESTAURANT_TOS_WITH_MENU_AND_RATING[SECOND],
                        RESTAURANT_TOS_WITH_MENU_AND_RATING[THIRD]));
    }

    @Test
    void getAllWithMenuAndRatingSortedByRatingAscMenuEntryNameDesc() {
        checkGet(restTemplate, "/api/public/restaurants?rating=true&menu=true" +
                        "&sort=rating,name,desc,menuEntry.name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[THIRD],
                        RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[SECOND],
                        RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[FIRST]));
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
                RESTAURANT_TOS_WITH_MENU[FIRST]);
    }

    @Test
    void getOneWithMenuSortedByNameDesc() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&sort=menuEntry.name,desc",
                RestaurantTo.class, RESTAURANT_TOS_WITH_MENU_DESC[FIRST]);
    }

    @Test
    void getOneWithRating() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true", RestaurantTo.class,
                RESTAURANT_TOS_WITH_RATING[FIRST]);
    }

    @Test
    void getOneWithRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true&ratingDate=" +
                        PREV_1D_DATE, RestaurantTo.class, RESTAURANT_TO_WITH_RATING_BY_DATE);
    }

    @Test
    void getOneWithRatingStartDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true&ratingDateStart=" +
                        PREV_1D_DATE, RestaurantTo.class, RESTAURANT_TO_WITH_RATING_START_DATE);
    }

    @Test
    void getOneWithRatingEndDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?rating=true&ratingDateEnd=" +
                        PREV_1D_DATE, RestaurantTo.class, RESTAURANT_TO_WITH_RATING_END_DATE);
    }

    @Test
    void getOneWithRatingBetweenDate() {
        checkGet(restTemplate, String.format("/api/public/restaurants/1?rating=true&" +
                        "ratingDateStart=%s&ratingDateEnd=%s", PREV_1D_DATE, PREV_1D_DATE),
                RestaurantTo.class, RESTAURANT_TO_WITH_RATING_BY_DATE);
    }

    @Test
    void getOneWithMenuAndRating() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true",
                RestaurantTo.class, RESTAURANT_TOS_WITH_MENU_AND_RATING[FIRST]);
    }

    @Test
    void getOneWithMenuAndRatingSortedByNameDesc() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&" +
                        "sort=menuEntry.name,desc", RestaurantTo.class,
                RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[FIRST]);
    }

    @Test
    void getOneWithMenuAndRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&ratingDate=" +
                        PREV_1D_DATE, RestaurantTo.class, RESTAURANT_TO_WITH_MENU_AND_RATING_DATE);
    }

    @Test
    void getOneWithMenuAndRatingStartDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&" +
                        "ratingDateStart=" + PREV_1D_DATE, RestaurantTo.class,
                RESTAURANT_TO_WITH_MENU_AND_RATING_START_DATE);
    }

    @Test
    void getOneWithMenuAndRatingEndDate() {
        checkGet(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&" +
                        "ratingDateEnd=" + PREV_1D_DATE, RestaurantTo.class,
                RESTAURANT_TO_WITH_MENU_AND_RATING_END_DATE);
    }

    @Test
    void getOneWithMenuAndRatingBetweenDate() {
        checkGet(restTemplate, String.format("/api/public/restaurants/1?menu=true&rating=true&" +
                        "ratingDateStart=%s&ratingDateEnd=%s", PREV_1D_DATE, PREV_1D_DATE),
                RestaurantTo.class, RESTAURANT_TO_WITH_MENU_AND_RATING_DATE);
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
                FULL_RATING[FIRST]);
    }

    @Test
    void getRatingByDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/rating?date=" + PREV_1D_DATE,
                Integer.class, PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingByStartDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/rating?dateStart=" + PREV_1D_DATE,
                Integer.class, START_PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingByEndDate() {
        checkGet(restTemplate, "/api/public/restaurants/1/rating?dateEnd=" + PREV_1D_DATE,
                Integer.class, END_PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingBetweenDate() {
        checkGet(restTemplate, String.format("/api/public/restaurants/1/rating?dateStart=%s&" +
                        "dateEnd=%s", PREV_1D_DATE, PREV_1D_DATE), Integer.class,
                PREV_1D_RATING[FIRST]);
    }
}
