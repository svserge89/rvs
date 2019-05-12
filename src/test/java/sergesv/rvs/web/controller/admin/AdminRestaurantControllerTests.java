package sergesv.rvs.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.util.TestUtil.RestaurantPageTo;
import sergesv.rvs.web.controller.AbstractControllerTests;
import sergesv.rvs.web.to.RestaurantTo;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AdminRestaurantControllerTests extends AbstractControllerTests {
    @Test
    void create() {
        checkPost(adminRestTemplate(), "/api/admin/restaurants", NEW_RESTAURANT_TO,
                "id");
    }

    @Test
    void createConflict() {
        checkPostConflict(adminRestTemplate(), "/api/admin/restaurants", RESTAURANT_TOS[FIRST]);
    }

    @Test
    void createForbidden() {
        checkPostForbidden(userRestTemplate(), "/api/admin/restaurants", NEW_RESTAURANT_TO);
    }

    @Test
    void createUnauthorized() {
        checkPostUnauthorized(restTemplate, "/api/admin/restaurants", NEW_RESTAURANT_TO);
    }

    @Test
    void update() {
        checkPut(adminRestTemplate(), "/api/admin/restaurants/1", NEW_RESTAURANT_TO,
                "/api/public/restaurants/1", "id");
    }

    @Test
    void updateNotFound() {
        checkPutNotFound(adminRestTemplate(), "/api/admin/restaurants/10", NEW_RESTAURANT_TO);
    }

    @Test
    void updateForbidden() {
        checkPutForbidden(userRestTemplate(), "/api/admin/restaurants/1", NEW_RESTAURANT_TO);
    }

    @Test
    void updateUnauthorized() {
        checkPutUnauthorized(restTemplate, "/api/admin/restaurants/1", NEW_RESTAURANT_TO);
    }

    @Test
    void delete() {
        checkDelete(adminRestTemplate(), "/api/admin/restaurants/1", RestaurantTo.class,
                "/api/public/restaurants/1");
    }

    @Test
    void deleteNotFound() {
        checkDeleteNotFound(adminRestTemplate(), "/api/admin/restaurants/10");
    }

    @Test
    void deleteForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/restaurants/1");
    }

    @Test
    void deleteUnauthorized() {
        checkDeleteUnauthorized(restTemplate, "/api/admin/restaurants/1");
    }

    @Test
    void deleteAll() {
        checkDeleteAll(adminRestTemplate(), "/api/admin/restaurants",
                new RestaurantPageTo(Collections.emptyList(), PAGE,
                        propertyResolver.getRestaurantPageSize(), TOTAL_EMPTY_PAGE),
                "/api/public/restaurants");
    }

    @Test
    void deleteAllForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/restaurants");
    }

    @Test
    void deleteAllUnauthorized() {
        checkDeleteUnauthorized(restTemplate, "/api/admin/restaurants");
    }
}
