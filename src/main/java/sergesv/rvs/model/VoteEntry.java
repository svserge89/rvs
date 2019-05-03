package sergesv.rvs.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NamedEntityGraph(name = VoteEntry.GRAPH_WITH_RESTAURANT,
        attributeNodes = @NamedAttributeNode("restaurant"))
@NamedQueries({
        @NamedQuery(name = VoteEntry.QUERY_GET_RATING_PAIR,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.QUERY_GET_RATING_PAIR_BY_DATE,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry WHERE voteEntry.date = :date " +
                    "GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.QUERY_GET_RATING_PAIR_BY_DATE_BETWEEN,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.date BETWEEN :dateStart AND :dateEnd " +
                    "GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.QUERY_DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE,
            query = "DELETE FROM VoteEntry voteEntry WHERE voteEntry.user.id = :userId " +
                        "AND voteEntry.restaurant.id = :restaurantId AND voteEntry.date = :date")
})
@Table(name = "vote_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VoteEntry implements EntityWithId {
    public static final String GRAPH_WITH_RESTAURANT = "VoteEntry_graphWithRestaurant";
    public static final String QUERY_GET_RATING_PAIR = "VoteEntry_getRatingPair";
    public static final String QUERY_GET_RATING_PAIR_BY_DATE = "VoteEntry_getRatingPairByDate";
    public static final String QUERY_GET_RATING_PAIR_BY_DATE_BETWEEN =
            "VoteEntry_gatRatingPairByDateBetween";
    public static final String QUERY_DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE =
            "VoteEntry_deleteByUserIdAndRestaurantIdAndDate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "time")
    private LocalTime time;
}
