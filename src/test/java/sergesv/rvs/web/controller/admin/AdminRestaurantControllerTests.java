package sergesv.rvs.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.util.TestUtil.RestaurantPageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AdminRestaurantControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RvsPropertyResolver propertyResolver;

    @Test
    void create() {
        checkPost(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants", NEW_RESTAURANT_TO, "id");
    }

    @Test
    void createConflict() {
        checkPostConflict(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants", RESTAURANT_TOS[FIRST]);
    }

    @Test
    void createForbidden() {
        checkPostForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants", NEW_RESTAURANT_TO);
    }

    @Test
    void update() {
        checkPut(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1", NEW_RESTAURANT_TO,
                "/api/public/restaurants/1", "id");
    }

    @Test
    void updateNotFound() {
        checkPutNotFound(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/10", NEW_RESTAURANT_TO);
    }

    @Test
    void updateForbidden() {
        checkPutForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1", NEW_RESTAURANT_TO);
    }

    @Test
    void delete() {
        checkDelete(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/1", RestaurantTo.class,
                "/api/public/restaurants/1");
    }

    @Test
    void deleteNotFound() {
        checkDeleteNotFound(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants/10");
    }

    @Test
    void deleteForbidden() {
        checkDeleteForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants/1");
    }

    @Test
    void deleteAll() {
        checkDeleteAll(restTemplate.withBasicAuth("admin", "password"),
                "/api/admin/restaurants", new RestaurantPageTo(Collections.emptyList(), PAGE,
                        propertyResolver.getRestaurantPageSize(), TOTAL_EMPTY_PAGE),
                "/api/public/restaurants");
    }

    @Test
    void deleteAllForbidden() {
        checkDeleteForbidden(restTemplate.withBasicAuth("user_1", "password"),
                "/api/admin/restaurants");
    }
}
