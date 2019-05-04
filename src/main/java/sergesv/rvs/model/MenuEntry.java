package sergesv.rvs.model;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

import static sergesv.rvs.util.ValidationUtil.MENU_ENTRY_NAME_SIZE;

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

    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
