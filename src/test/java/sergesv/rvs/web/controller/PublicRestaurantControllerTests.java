package sergesv.rvs.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.util.TestUtil;
import sergesv.rvs.util.TestUtil.RestaurantPageTo;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.checkGetNotFoundResponse;
import static sergesv.rvs.util.TestUtil.checkGetResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class PublicRestaurantControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RvsPropertyResolver propertyResolver;

    @Test
    void getAll() {
        checkGetResponse(restTemplate, "/api/public/restaurants", RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(), TOTAL_PAGES,
                        RESTAURANT_TOS[FIRST], RESTAURANT_TOS[SECOND], RESTAURANT_TOS[THIRD]));
    }

    @Test
    void getAllWithMenu() {
        checkGetResponse(restTemplate, "/api/public/restaurants?menu=true",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU[FIRST],
                        RESTAURANT_TOS_WITH_MENU[SECOND], RESTAURANT_TOS_WITH_MENU[THIRD]));
    }

    @Test
    void getAllWithMenuSortedByNameDescMenuEntryNameDesc() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants?menu=true&sort=name,desc,menuEntry.name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_DESC[THIRD],
                        RESTAURANT_TOS_WITH_MENU_DESC[SECOND],
                        RESTAURANT_TOS_WITH_MENU_DESC[FIRST]));
    }

    @Test
    void getAllWithMenuByDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants?menu=true&menuDate=" +
                        PREV_1D_DATE, RestaurantPageTo.class,
                buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[FIRST],
                        RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[SECOND],
                        RESTAURANT_TOS_WITH_MENU_BY_PREV_1D[THIRD]));
    }

    @Test
    void getAllWithRating() {
        checkGetResponse(restTemplate, "/api/public/restaurants?rating=true",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_RATING[FIRST],
                        RESTAURANT_TOS_WITH_RATING[SECOND], RESTAURANT_TOS_WITH_RATING[THIRD]));
    }

    @Test
    void getAllWithRatingSortedByRatingAscNameDesc() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants?rating=true&sort=rating,name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_RATING[THIRD],
                        RESTAURANT_TOS_WITH_RATING[SECOND], RESTAURANT_TOS_WITH_RATING[FIRST]));
    }

    @Test
    void getAllWithMenuAndRating() {
        checkGetResponse(restTemplate, "/api/public/restaurants?rating=true&menu=true",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_AND_RATING[FIRST],
                        RESTAURANT_TOS_WITH_MENU_AND_RATING[SECOND],
                        RESTAURANT_TOS_WITH_MENU_AND_RATING[THIRD]));
    }

    @Test
    void getAllWithMenuAndRatingSortedByRatingAscMenuEntryNameDesc() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants?rating=true&menu=true" +
                        "&sort=rating,name,desc,menuEntry.name,desc",
                RestaurantPageTo.class, buildPageTo(PAGE, propertyResolver.getRestaurantPageSize(),
                        TOTAL_PAGES, RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[THIRD],
                        RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[SECOND],
                        RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[FIRST]));
    }

    @Test
    void getOne() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1", RestaurantTo.class,
                RESTAURANT_TOS[FIRST]);
    }

    @Test
    void getOneNotFound() {
        checkGetNotFoundResponse(restTemplate, "/api/public/restaurants/10");
    }

    @Test
    void getOneWithMenu() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1?menu=true",
                RestaurantTo.class, RESTAURANT_TOS_WITH_MENU[FIRST]);
    }

    @Test
    void getOneWithMenuSortedByNameDesc() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1?menu=true&" +
                        "sort=menuEntry.name,desc", RestaurantTo.class,
                RESTAURANT_TOS_WITH_MENU_DESC[FIRST]);
    }

    @Test
    void getOneWithRating() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1?rating=true",
                RestaurantTo.class, RESTAURANT_TOS_WITH_RATING[FIRST]);
    }

    @Test
    void getOneWithRatingByDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?rating=true&ratingDate=" + PREV_1D_DATE,
                RestaurantTo.class, RESTAURANT_TO_WITH_RATING_BY_DATE);
    }

    @Test
    void getOneWithRatingStartDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?rating=true&ratingDateStart=" + PREV_1D_DATE,
                RestaurantTo.class, RESTAURANT_TO_WITH_RATING_START_DATE);
    }

    @Test
    void getOneWithRatingEndDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?rating=true&ratingDateEnd=" + PREV_1D_DATE,
                RestaurantTo.class, RESTAURANT_TO_WITH_RATING_END_DATE);
    }

    @Test
    void getOneWithRatingBetweenDate() {
        checkGetResponse(restTemplate,
                String.format("/api/public/restaurants/1?rating=true&ratingDateStart=%s&" +
                        "ratingDateEnd=%s", PREV_1D_DATE, PREV_1D_DATE), RestaurantTo.class,
                RESTAURANT_TO_WITH_RATING_BY_DATE);
    }

    @Test
    void getOneWithMenuAndRating() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1?menu=true&rating=true",
                RestaurantTo.class, RESTAURANT_TOS_WITH_MENU_AND_RATING[FIRST]);
    }

    @Test
    void getOneWithMenuAndRatingSortedByNameDesc() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1?menu=true&rating=true&" +
                        "sort=menuEntry.name,desc", RestaurantTo.class,
                RESTAURANT_TOS_WITH_MENU_DESC_AND_RATING[FIRST]);
    }

    @Test
    void getOneWithMenuAndRatingByDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?menu=true&rating=true&ratingDate=" + PREV_1D_DATE,
                RestaurantTo.class, RESTAURANT_TO_WITH_MENU_AND_RATING_DATE);
    }

    @Test
    void getOneWithMenuAndRatingStartDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?menu=true&rating=true&ratingDateStart=" +
                        PREV_1D_DATE, RestaurantTo.class,
                RESTAURANT_TO_WITH_MENU_AND_RATING_START_DATE);
    }

    @Test
    void getOneWithMenuAndRatingEndDate() {
        checkGetResponse(restTemplate,
                "/api/public/restaurants/1?menu=true&rating=true&ratingDateEnd=" +
                        PREV_1D_DATE, RestaurantTo.class,
                RESTAURANT_TO_WITH_MENU_AND_RATING_END_DATE);
    }

    @Test
    void getOneWithMenuAndRatingBetweenDate() {
        checkGetResponse(restTemplate,
                String.format("/api/public/restaurants/1?menu=true&rating=true&" +
                        "ratingDateStart=%s&ratingDateEnd=%s", PREV_1D_DATE, PREV_1D_DATE),
                RestaurantTo.class, RESTAURANT_TO_WITH_MENU_AND_RATING_DATE);
    }

    @Test
    void getMenu() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu",
                TestUtil.MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        CURRENT_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[THIRD],
                        PREV_2D_MENU_ENTRY_TOS[SECOND], PREV_2D_MENU_ENTRY_TOS[FIRST],
                        PREV_2D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuSortedByNameDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu?sort=name,date",
                TestUtil.MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_2D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[FIRST], PREV_2D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[SECOND],
                        PREV_2D_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[THIRD],
                        CURRENT_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu?date=" + PREV_1D_DATE,
                TestUtil.MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByStartDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu?dateStart=" +
                        PREV_1D_DATE, TestUtil.MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        CURRENT_MENU_ENTRY_TOS[SECOND], CURRENT_MENU_ENTRY_TOS[FIRST],
                        CURRENT_MENU_ENTRY_TOS[THIRD], PREV_1D_MENU_ENTRY_TOS[SECOND],
                        PREV_1D_MENU_ENTRY_TOS[FIRST], PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByEndDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu?dateEnd=" + PREV_1D_DATE,
                TestUtil.MenuEntryPageTo.class, buildPageTo(PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD], PREV_2D_MENU_ENTRY_TOS[SECOND],
                        PREV_2D_MENU_ENTRY_TOS[FIRST], PREV_2D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuByDateBetween() {
        checkGetResponse(restTemplate,
                String.format("/api/public/restaurants/1/menu?dateStart=%s&dateEnd=%s",
                        PREV_1D_DATE, PREV_1D_DATE), TestUtil.MenuEntryPageTo.class,
                buildPageTo(PAGE, propertyResolver.getMenuEntryPageSize(), TOTAL_PAGES,
                        PREV_1D_MENU_ENTRY_TOS[SECOND], PREV_1D_MENU_ENTRY_TOS[FIRST],
                        PREV_1D_MENU_ENTRY_TOS[THIRD]));
    }

    @Test
    void getMenuEntry() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/menu/311", MenuEntryTo.class,
                CURRENT_MENU_ENTRY_TOS[FIRST]);
    }

    @Test
    void getMenuEntryNotFound() {
        checkGetNotFoundResponse(restTemplate, "/api/public/restaurants/1/menu/321");
    }

    @Test
    void getRating() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/rating", Integer.class,
                FULL_RATING[FIRST]);
    }

    @Test
    void getRatingByDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/rating?date=" + PREV_1D_DATE,
                Integer.class, PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingByStartDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/rating?dateStart=" +
                        PREV_1D_DATE, Integer.class, START_PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingByEndDate() {
        checkGetResponse(restTemplate, "/api/public/restaurants/1/rating?dateEnd=" +
                PREV_1D_DATE, Integer.class, END_PREV_1D_RATING[FIRST]);
    }

    @Test
    void getRatingBetweenDate() {
        checkGetResponse(restTemplate,
                String.format("/api/public/restaurants/1/rating?dateStart=%s&dateEnd=%s",
                        PREV_1D_DATE, PREV_1D_DATE), Integer.class, PREV_1D_RATING[FIRST]);
    }
}
