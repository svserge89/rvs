package sergesv.rvs.web.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PageTo<T> {
    private final List<T> content;

    private final Integer current;

    private final Integer size;

    private final Integer total;
}
