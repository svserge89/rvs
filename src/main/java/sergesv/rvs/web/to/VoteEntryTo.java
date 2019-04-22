package sergesv.rvs.web.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class VoteEntryTo {
    private final RestaurantTo restaurant;

    private final LocalDateTime dateTime;
}
