package sergesv.rvs.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@NamedEntityGraph(name = Restaurant.GRAPH_WITH_MENU_ENTRIES,
        attributeNodes = @NamedAttributeNode("menuEntry"))
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant implements EntityWithId {
    public static final String GRAPH_WITH_MENU_ENTRIES = "Restaurant_graphWithMenuEntry";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<MenuEntry> menuEntry;
}
