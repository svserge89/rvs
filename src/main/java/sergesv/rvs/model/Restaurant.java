package sergesv.rvs.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = Restaurant.FIND_ALL_WITH_MENU,
            query = "SELECT DISTINCT restaurant FROM Restaurant restaurant " +
                    "INNER JOIN FETCH restaurant.menuEntries menuEntry " +
                    "WHERE menuEntry.date = :menuDate"),
        @NamedQuery(name = Restaurant.FIND_ONE_WITH_MENU,
            query = "SELECT restaurant FROM Restaurant restaurant " +
                    "INNER JOIN FETCH restaurant.menuEntries menuEntry " +
                    "WHERE restaurant.id = :id AND menuEntry.date = :menuDate"),
        @NamedQuery(name = Restaurant.COUNT_ALL_WITH_MENU,
            query = "SELECT COUNT(DISTINCT restaurant) FROM Restaurant restaurant " +
                    "INNER JOIN restaurant.menuEntries menuEntry " +
                    "WHERE menuEntry.date = :menuDate")
})
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant implements EntityWithId {
    public static final String FIND_ALL_WITH_MENU = "Restaurant.findAllWithMenu";
    public static final String FIND_ONE_WITH_MENU = "Restaurant.findByIdWithMenu";
    public static final String COUNT_ALL_WITH_MENU = "Restaurant.countAllWithMenu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<MenuEntry> menuEntries;
}
