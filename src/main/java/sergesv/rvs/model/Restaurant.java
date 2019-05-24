package sergesv.rvs.model;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

import static sergesv.rvs.util.ValidationUtil.RESTAURANT_NAME_SIZE;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant implements EntityWithId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty
    @SafeHtml
    @Size(max = RESTAURANT_NAME_SIZE)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<MenuEntry> menuEntry;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<VoteEntry> voteEntry;
}
