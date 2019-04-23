package sergesv.rvs.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {
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
