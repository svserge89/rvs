package sergesv.rvs.util.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public final class ControllerUtil {
    public static Pageable resolvePageable(Integer page, Integer size, Sort sort,
                                           int defaultPageSize, Sort defaultSort) {
        return PageRequest.of(Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(defaultPageSize),
                Optional.ofNullable(sort).orElse(defaultSort));
    }

    private ControllerUtil() {
    }
}
