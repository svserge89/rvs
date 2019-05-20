package sergesv.rvs.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantWithRating {
    @EqualsAndHashCode.Include
    private final long id;

    private final String name;

    private final long rating;
}
