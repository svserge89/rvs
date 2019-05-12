package sergesv.rvs.web.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sergesv.rvs.util.DateTimeUtil;
import sergesv.rvs.util.TestUtil.VoteEntryPageTo;
import sergesv.rvs.web.controller.AbstractControllerTests;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import static sergesv.rvs.util.TestData.*;
import static sergesv.rvs.util.TestUtil.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class UserVoteControllerTests extends AbstractControllerTests {
    @Test
    void getAll() {
        checkGet(userRestTemplate(), "/api/user/votes", VoteEntryPageTo.class,
                buildPageTo(PAGE, propertyResolver.getVoteEntryPageSize(), TOTAL_PAGES,
                        USER_1_VOTE_ENTRY_TOS[THIRD], USER_1_VOTE_ENTRY_TOS[SECOND],
                        USER_1_VOTE_ENTRY_TOS[FIRST]));
    }

    @Test
    void getAllSortedByDate() {
        checkGet(userRestTemplate(), "/api/user/votes?sort=date", VoteEntryPageTo.class,
                buildPageTo(PAGE, propertyResolver.getVoteEntryPageSize(), TOTAL_PAGES,
                        USER_1_VOTE_ENTRY_TOS[FIRST], USER_1_VOTE_ENTRY_TOS[SECOND],
                        USER_1_VOTE_ENTRY_TOS[THIRD]));
    }

    @Test
    void getAllUnauthorized() {
        checkGetUnauthorized(restTemplate, "/api/user/votes");
    }

    @Test
    void getAllStartDate() {
        checkGet(userRestTemplate(), "/api/user/votes?dateStart=" + PREV_1D_DATE,
                VoteEntryPageTo.class, buildPageTo(PAGE, propertyResolver.getVoteEntryPageSize(),
                        TOTAL_PAGES, USER_1_VOTE_ENTRY_TOS[THIRD], USER_1_VOTE_ENTRY_TOS[SECOND]));
    }

    @Test
    void getAllEndDate() {
        checkGet(userRestTemplate(), "/api/user/votes?dateEnd=" + PREV_1D_DATE,
                VoteEntryPageTo.class, buildPageTo(PAGE, propertyResolver.getVoteEntryPageSize(),
                        TOTAL_PAGES, USER_1_VOTE_ENTRY_TOS[SECOND], USER_1_VOTE_ENTRY_TOS[FIRST]));
    }

    @Test
    void getAllBetweenDate() {
        checkGet(userRestTemplate(), String.format("/api/user/votes?dateStart=%s&dateEnd=%s",
                PREV_1D_DATE, PREV_1D_DATE), VoteEntryPageTo.class, buildPageTo(PAGE,
                propertyResolver.getVoteEntryPageSize(),
                TOTAL_PAGES, USER_1_VOTE_ENTRY_TOS[SECOND]));
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createNew() {
        checkPost(restTemplate.withBasicAuth(USER_2.getNickName(), USER_2.getPassword()),
                "/api/user/restaurants/1/vote", "/api/public/restaurants/1/rating",
                4);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createExistsBeforeOrEqualMaxVoteTime() {
        DateTimeUtil.setTestLocalTime(propertyResolver.getMaxVoteTime());

        checkPost(userRestTemplate(), "/api/user/restaurants/1/vote",
                "/api/public/restaurants/1/rating", FULL_RATING[FIRST] + 1);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createExistsAfterMaxVoteTime() {
        DateTimeUtil.setTestLocalTime(propertyResolver.getMaxVoteTime().plusMinutes(1));

        checkPostConflict(userRestTemplate(), "/api/user/restaurants/1/vote");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void createUnauthorized() {
        checkPostUnauthorized(restTemplate, "/api/user/restaurants/1/vote");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteBeforeOrEqualMaxVoteTime() {
        DateTimeUtil.setTestLocalTime(propertyResolver.getMaxVoteTime());

        checkDelete(userRestTemplate(), "/api/user/restaurants/2/vote",
                "/api/public/restaurants/2/rating", FULL_RATING[SECOND] - 1);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteBeforeOrEqualMaxVoteTimeNotFound() {
        DateTimeUtil.setTestLocalTime(propertyResolver.getMaxVoteTime());

        checkDeleteNotFound(userRestTemplate(), "/api/user/restaurants/1/vote");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteUnauthorized() {
        checkDeleteUnauthorized(restTemplate, "/api/user/restaurants/2/vote");
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    void deleteAfterMaxVoteTime() {
        DateTimeUtil.setTestLocalTime(propertyResolver.getMaxVoteTime().plusMinutes(1));

        checkDeleteConflict(userRestTemplate(), "/api/user/restaurants/2/vote");
    }
}
