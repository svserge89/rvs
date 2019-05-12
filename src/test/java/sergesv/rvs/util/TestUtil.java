package sergesv.rvs.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import sergesv.rvs.web.to.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

public final class TestUtil {
    public static <T> void checkGet(TestRestTemplate restTemplate, String url,
                                    Class<? extends T> toClass, T expected,
                                    String... ignoredFields) {
        var response = restTemplate.getForEntity(url, toClass);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON_UTF8);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expected, ignoredFields);
    }

    public static void checkGetNotFound(TestRestTemplate restTemplate, String url) {
        assertError(restTemplate.getForEntity(url, Object.class), HttpStatus.NOT_FOUND);
    }

    public static void checkGetForbidden(TestRestTemplate restTemplate, String url) {
        assertError(restTemplate.getForEntity(url, Object.class), HttpStatus.FORBIDDEN);
    }

    public static void checkGetUnauthorized(TestRestTemplate restTemplate, String url) {
        assertError(restTemplate.getForEntity(url, Object.class), HttpStatus.UNAUTHORIZED);
    }

    public static <T> void checkPost(TestRestTemplate restTemplate, String url, T toEntry,
                                     String... ignoredFields) {
        var response = restTemplate.postForEntity(url, toEntry, toEntry.getClass());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON_UTF8);
        assertThat(response.getBody())
                .isEqualToIgnoringGivenFields(toEntry, ignoredFields);
    }

    public static <T> void checkPost(TestRestTemplate restTemplate, String url, String checkUrl,
                                     T checkValue) {
        var response = getPostResponseEntity(restTemplate, url);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON_UTF8);
        assertThat(restTemplate.getForObject(checkUrl, checkValue.getClass()))
                .isEqualTo(checkValue);
    }

    public static void checkPostConflict(TestRestTemplate restTemplate, String url,
                                         Object toEntry) {
        assertError(restTemplate.postForEntity(url, toEntry, Object.class), HttpStatus.CONFLICT);
    }

    public static void checkPostConflict(TestRestTemplate restTemplate, String url) {
        assertError(getPostResponseEntity(restTemplate, url), HttpStatus.CONFLICT);
    }

    public static void checkPostForbidden(TestRestTemplate restTemplate, String url,
                                          Object toEntry) {
        assertError(restTemplate.postForEntity(url, toEntry, Object.class), HttpStatus.FORBIDDEN);
    }

    public static void checkPostUnauthorized(TestRestTemplate restTemplate, String url,
                                             Object toEntry) {
        assertError(restTemplate.postForEntity(url, toEntry, Object.class),
                HttpStatus.UNAUTHORIZED);
    }

    public static void checkPostUnauthorized(TestRestTemplate restTemplate, String url) {
        assertError(getPostResponseEntity(restTemplate, url), HttpStatus.UNAUTHORIZED);
    }

    public static <T> void checkPut(TestRestTemplate restTemplate, String url, T toEntry,
                                    String checkUrl, String... ignoredFields) {
        var response = getPutResponseEntity(restTemplate, url, toEntry);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isFalse();
        assertThat(restTemplate.getForObject(checkUrl, toEntry.getClass()))
                .isEqualToIgnoringGivenFields(toEntry, ignoredFields);
    }

    public static void checkPutNotFound(TestRestTemplate restTemplate, String url, Object toEntry) {
        assertError(getPutResponseEntity(restTemplate, url, toEntry), HttpStatus.NOT_FOUND);
    }

    public static void checkPutForbidden(TestRestTemplate restTemplate, String url,
                                         Object toEntry) {
        assertError(getPutResponseEntity(restTemplate, url, toEntry), HttpStatus.FORBIDDEN);
    }

    public static void checkPutUnauthorized(TestRestTemplate restTemplate, String url,
                                            Object toEntry) {
        assertError(getPutResponseEntity(restTemplate, url, toEntry), HttpStatus.UNAUTHORIZED);
    }

    public static <T> void checkDelete(TestRestTemplate restTemplate, String url, Class<T> toClass,
                                       String checkUrl) {
        var response = getDeleteResponseEntity(restTemplate, url);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
        assertThat(restTemplate.getForEntity(checkUrl, toClass).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    public static <T> void checkDelete(TestRestTemplate restTemplate, String url, String checkUrl,
                                       T checkValue) {
        var response = getDeleteResponseEntity(restTemplate, url);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
        assertThat(restTemplate.getForObject(checkUrl, checkValue.getClass()))
                .isEqualTo(checkValue);
    }

    public static void checkDeleteConflict(TestRestTemplate restTemplate, String url) {
        assertError(getDeleteResponseEntity(restTemplate, url), HttpStatus.CONFLICT);
    }

    public static void checkDeleteNotFound(TestRestTemplate restTemplate, String url) {
        assertError(getDeleteResponseEntity(restTemplate, url), HttpStatus.NOT_FOUND);
    }

    public static void checkDeleteForbidden(TestRestTemplate restTemplate, String url) {
        assertError(getDeleteResponseEntity(restTemplate, url), HttpStatus.FORBIDDEN);
    }

    public static void checkDeleteUnauthorized(TestRestTemplate restTemplate, String url) {
        assertError(getDeleteResponseEntity(restTemplate, url), HttpStatus.UNAUTHORIZED);
    }

    public static <T> void checkDeleteAll(TestRestTemplate restTemplate, String url, T emptyToEntry,
                                          String checkUrl) {
        var response = getDeleteResponseEntity(restTemplate, url);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
        assertThat(restTemplate.getForObject(checkUrl, emptyToEntry.getClass()))
                .isEqualTo(emptyToEntry);
    }

    public static class RestaurantPageTo extends PageTo<RestaurantTo> {
        public RestaurantPageTo(List<RestaurantTo> content, Integer current, Integer size,
                                Integer total) {
            super(content, current, size, total);
        }
    }

    public static class MenuEntryPageTo extends PageTo<MenuEntryTo> {
        public MenuEntryPageTo(List<MenuEntryTo> content, Integer current, Integer size,
                               Integer total) {
            super(content, current, size, total);
        }
    }

    public static class UserPageTo extends PageTo<UserTo> {
        public UserPageTo(List<UserTo> content, Integer current, Integer size, Integer total) {
            super(content, current, size, total);
        }
    }

    public static class VoteEntryPageTo extends PageTo<VoteEntryTo> {
        public VoteEntryPageTo(List<VoteEntryTo> content, Integer current, Integer size,
                               Integer total) {
            super(content, current, size, total);
        }
    }

    private static ResponseEntity<Object> getPutResponseEntity(TestRestTemplate restTemplate,
                                                               String url, Object toEntry) {
        var httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(APPLICATION_JSON_UTF8);

        var httpEntity = new HttpEntity<>(toEntry, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class);
    }

    private static ResponseEntity<Object> getDeleteResponseEntity(TestRestTemplate restTemplate,
                                                                  String url) {
        var httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(APPLICATION_JSON_UTF8);

        var httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Object.class);
    }

    private static ResponseEntity<Object> getPostResponseEntity(TestRestTemplate restTemplate,
                                                                String url) {
        var httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(APPLICATION_JSON_UTF8);

        var httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
    }

    private static void assertError(ResponseEntity<Object> response, HttpStatus httpStatus) {
        var map = (Map)Optional.ofNullable(response.getBody()).orElse(Collections.emptyMap());

        assertThat(response.getStatusCode()).isEqualTo(httpStatus);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON_UTF8);
        assertThat(map.get("status")).isEqualTo(httpStatus.value());
    }

    private TestUtil() {
    }
}
