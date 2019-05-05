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
@NamedEntityGraphs({
        @NamedEntityGraph(name = Restaurant.GRAPH_WITH_MENU,
                attributeNodes = @NamedAttributeNode("menuEntry")),
        @NamedEntityGraph(name = Restaurant.GRAPH_WITH_VOTE,
                attributeNodes = @NamedAttributeNode("voteEntry")),
        @NamedEntityGraph(name = Restaurant.GRAPH_WITH_MENU_AND_VOTE,
                attributeNodes = {@NamedAttributeNode("menuEntry"),
                                  @NamedAttributeNode("voteEntry")})
})
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant implements EntityWithId {
    public static final String GRAPH_WITH_MENU = "Restaurant_graphWithMenuEntry";
    public static final String GRAPH_WITH_VOTE = "Restaurant_graphWithVoteEntry";
    public static final String GRAPH_WITH_MENU_AND_VOTE =
            "Restaurant_graphWithMenuEntryAndVoteEntry";

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
