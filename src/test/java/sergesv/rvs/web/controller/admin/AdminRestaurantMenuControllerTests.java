package sergesv.rvs.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.web.to.MenuEntryTo;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AdminRestaurantMenuControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RvsPropertyResolver propertyResolver;

    @Test
    void create() {
        checkPost(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu", NEW_MENU_ENTRY_TO, "id");
    }

    @Test
    void createConflict() {
        checkPostConflict(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu", CURRENT_MENU_ENTRY_TOS[FIRST]);
    }

    @Test
    void createForbidden() {
        checkPostForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1/menu", NEW_MENU_ENTRY_TO);
    }

    @Test
    void update() {
        checkPut(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu/311", NEW_MENU_ENTRY_TO,
                "/api/public/restaurants/1/menu/311", "id");
    }

    @Test
    void updateNotFound() {
        checkPutNotFound(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu/600", NEW_MENU_ENTRY_TO);
    }

    @Test
    void updateForbidden() {
        checkPutForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1/menu/311", NEW_MENU_ENTRY_TO);
    }

    @Test
    void delete() {
        checkDelete(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu/311", MenuEntryTo.class,
                "/api/public/restaurants/1/menu/311");
    }

    @Test
    void deleteNotFound() {
        checkDeleteNotFound(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1/menu/600");
    }

    @Test
    void deleteForbidden() {
        checkDeleteForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1/menu/311");
    }

    @Test
    void deleteAll() {
        deleteAllHelper("/api/admin/restaurants/1/menu",
                "/api/public/restaurants/1/menu?date=" + CURRENT_DATE);
    }

    @Test
    void deleteAllForbidden() {
        checkDeleteForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1/menu");
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
        checkDeleteAll(restTemplate.withBasicAuth("admin", "password"), url,
                new MenuEntryPageTo(Collections.emptyList(), PAGE,
                        propertyResolver.getMenuEntryPageSize(), TOTAL_EMPTY_PAGE), checkUrl);
    }
}
