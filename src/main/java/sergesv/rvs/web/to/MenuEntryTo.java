package sergesv.rvs.web.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class MenuEntryTo {
    private final long id;

    private final String name;

    private final BigDecimal price;

    private final LocalDate date;
}
