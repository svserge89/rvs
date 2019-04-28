package sergesv.rvs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RatingPair {
    @EqualsAndHashCode.Include
    private final Restaurant restaurant;

    private final long rating;
}
