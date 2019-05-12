package sergesv.rvs.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.web.controller.AbstractControllerTests;
import sergesv.rvs.web.to.MenuEntryTo;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AdminRestaurantMenuControllerTests extends AbstractControllerTests {
    @Test
    void create() {
        checkPost(adminRestTemplate(), "/api/admin/restaurants/1/menu", NEW_MENU_ENTRY_TO,
                "id");
    }

    @Test
    void createConflict() {
        checkPostConflict(adminRestTemplate(), "/api/admin/restaurants/1/menu",
                CURRENT_MENU_ENTRY_TOS[FIRST]);
    }

    @Test
    void createForbidden() {
        checkPostForbidden(userRestTemplate(), "/api/admin/restaurants/1/menu",
                NEW_MENU_ENTRY_TO);
    }

    @Test
    void createUnauthorized() {
        checkPostUnauthorized(restTemplate, "/api/admin/restaurants/1/menu",
                NEW_MENU_ENTRY_TO);
    }

    @Test
    void update() {
        checkPut(adminRestTemplate(), "/api/admin/restaurants/1/menu/311", NEW_MENU_ENTRY_TO,
                "/api/public/restaurants/1/menu/311", "id");
    }

    @Test
    void updateNotFound() {
        checkPutNotFound(adminRestTemplate(), "/api/admin/restaurants/1/menu/600",
                NEW_MENU_ENTRY_TO);
    }

    @Test
    void updateForbidden() {
        checkPutForbidden(userRestTemplate(), "/api/admin/restaurants/1/menu/311",
                NEW_MENU_ENTRY_TO);
    }

    @Test
    void updateUnauthorized() {
        checkPutUnauthorized(restTemplate, "/api/admin/restaurants/1/menu/311",
                NEW_MENU_ENTRY_TO);
    }

    @Test
    void delete() {
        checkDelete(adminRestTemplate(), "/api/admin/restaurants/1/menu/311", MenuEntryTo.class,
                "/api/public/restaurants/1/menu/311");
    }

    @Test
    void deleteNotFound() {
        checkDeleteNotFound(adminRestTemplate(), "/api/admin/restaurants/1/menu/600");
    }

    @Test
    void deleteForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/restaurants/1/menu/311");
    }

    @Test
    void deleteUnauthorized() {
        checkDeleteUnauthorized(restTemplate, "/api/admin/restaurants/1/menu/311");
    }

    @Test
    void deleteAll() {
        deleteAllHelper("/api/admin/restaurants/1/menu",
                "/api/public/restaurants/1/menu?date=" + CURRENT_DATE);
    }

    @Test
    void deleteAllForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/restaurants/1/menu");
    }

    @Test
    void deleteAllUnauthorized() {
        checkDeleteUnauthorized(restTemplate, "/api/admin/restaurants/1/menu");
    }

    @Test
    void deleteAllByDate() {
        deleteAllHelper("/api/admin/restaurants/1/menu?date=" + PREV_1D_DATE,
                "/api/public/restaurants/1/menu?date=" + PREV_1D_DATE);
    }

    @Test
    void deleteAllByStartDate() {
        deleteAllHelper("/api/admin/restaurants/1/menu?dateStart=" + PREV_1D_DATE,
                "/api/public/restaurants/1/menu?dateStart=" + PREV_1D_DATE);
    }

    @Test
    void deleteAllByEndDate() {
        deleteAllHelper("/api/admin/restaurants/1/menu?dateEnd=" + PREV_1D_DATE,
                "/api/public/restaurants/1/menu?dateEnd=" + PREV_1D_DATE);
    }

    @Test
    void deleteAllBetweenDate() {
        deleteAllHelper(String.format("/api/admin/restaurants/1/menu?dateStart=%s&dateEnd=%s",
                PREV_1D_DATE, PREV_1D_DATE),
                String.format("/api/public/restaurants/1/menu?dateStart=%s&dateEnd=%s",
                        PREV_1D_DATE, PREV_1D_DATE));

    }

    private void deleteAllHelper(String url, String checkUrl) {
        checkDeleteAll(adminRestTemplate(), url, new MenuEntryPageTo(Collections.emptyList(), PAGE,
                propertyResolver.getMenuEntryPageSize(), TOTAL_EMPTY_PAGE), checkUrl);
    }
}
