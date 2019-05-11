package sergesv.rvs.web.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.util.TestUtil.UserPageTo;
import sergesv.rvs.web.controller.AbstractControllerTests;
import sergesv.rvs.web.to.UserTo;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class AdminUserControllerTests extends AbstractControllerTests {
    @Test
    void getAll() {
        checkGet(adminRestTemplate(), "/api/admin/users", UserPageTo.class, buildPageTo(PAGE,
                propertyResolver.getUserPageSize(), TOTAL_PAGES, ADMIN, USER_1, USER_2));
    }

    @Test
    void getAllSortedByEmailDesc() {
        checkGet(adminRestTemplate(), "/api/admin/users?sort=email,desc", UserPageTo.class,
                buildPageTo(PAGE, propertyResolver.getUserPageSize(), TOTAL_PAGES, USER_2, USER_1,
                        ADMIN));
    }

    @Test
    void getAllForbidden() {
        checkGetForbidden(userRestTemplate(), "/api/admin/users");
    }

    @Test
    void getOne() {
        checkGet(adminRestTemplate(), "/api/admin/users/2", UserTo.class, USER_1,
                "password");
    }

    @Test
    void getOneNotFound() {
        checkGetNotFound(adminRestTemplate(), "/api/admin/users/10");
    }

    @Test
    void getOneForbidden() {
        checkGetForbidden(userRestTemplate(), "/api/admin/users/1");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void create() {
        checkPost(adminRestTemplate(), "/api/admin/users", NEW_USER_TO, "id",
                "password");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createConflict() {
        checkPostConflict(adminRestTemplate(), "/api/admin/users", USER_2);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createForbidden() {
        checkPostForbidden(userRestTemplate(), "/api/admin/users", NEW_USER_TO);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void update() {
        checkPut(adminRestTemplate(), "/api/admin/users/2", NEW_USER_TO,
                "/api/admin/users/2", "id", "password");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void updateNotFound() {
        checkPutNotFound(adminRestTemplate(), "/api/admin/users/10", NEW_USER_TO);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void updateForbidden() {
        checkPutForbidden(userRestTemplate(), "/api/admin/users/2", NEW_USER_TO);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void delete() {
        checkDelete(adminRestTemplate(), "/api/admin/users/2", UserTo.class,
                "/api/admin/users/2");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteConflict() {
        checkDeleteConflict(adminRestTemplate(), "/api/admin/users/1");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteNotFound() {
        checkDeleteNotFound(adminRestTemplate(), "/api/admin/users/10");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/users/1");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteAll() {
        checkDeleteAll(adminRestTemplate(), "/api/admin/users", new UserPageTo(List.of(ADMIN),
                        PAGE, propertyResolver.getUserPageSize(), TOTAL_PAGES),
                "/api/admin/users");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteAllForbidden() {
        checkDeleteForbidden(userRestTemplate(), "/api/admin/users");
    }
}
