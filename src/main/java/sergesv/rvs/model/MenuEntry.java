package sergesv.rvs.model;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static sergesv.rvs.util.ValidationUtil.*;

@Entity
@Table(name = "menu_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuEntry implements EntityWithId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty
    @SafeHtml
    @Size(max = MENU_ENTRY_NAME_SIZE)
    @Column(name = "name")
    private String name;

    @NotNull
    @Digits(integer = PRICE_INTEGER, fraction = PRICE_FRACTION)
    @DecimalMin(MIN_PRICE_VALUE)
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
