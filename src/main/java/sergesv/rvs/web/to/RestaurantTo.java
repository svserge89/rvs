package sergesv.rvs.web.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class RestaurantTo {
    private final long id;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LinkedHashSet<MenuEntryTo> menu;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer rating;
}
