package sergesv.rvs.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class TestUtil {
    public static <T> void checkGetResponse(TestRestTemplate restTemplate, String url,
                                             Class<? extends T> toClass, T expected) {
        var response = restTemplate.getForEntity(url, toClass);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(expected, response.getBody());
    }

    public static void checkGetNotFoundResponse(TestRestTemplate restTemplate, String url) {
        var response = restTemplate.getForEntity(url, Object.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
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

    private TestUtil() {
    }
}
