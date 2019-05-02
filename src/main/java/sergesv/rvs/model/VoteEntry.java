package sergesv.rvs.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NamedQueries({
        @NamedQuery(name = VoteEntry.FIND_ALL_BY_USER_ID,
            query = "SELECT voteEntry FROM VoteEntry voteEntry " +
                    "INNER JOIN FETCH voteEntry.restaurant restaurant " +
                    "WHERE voteEntry.user.id = :userId"),
        @NamedQuery(name = VoteEntry.FIND_ALL_BY_USER_ID_AND_DATE_BETWEEN,
            query = "SELECT voteEntry FROM VoteEntry voteEntry " +
                    "INNER JOIN FETCH voteEntry.restaurant restaurant " +
                    "WHERE voteEntry.user.id = :userId " +
                        "AND voteEntry.date BETWEEN :dateStart AND :dateEnd"),
        @NamedQuery(name = VoteEntry.COUNT_ALL_BY_USER_ID,
            query = "SELECT COUNT (voteEntry) FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.user.id = :userId"),
        @NamedQuery(name = VoteEntry.COUNT_ALL_BY_USER_ID_AND_DATE_BETWEEN,
            query = "SELECT COUNT (voteEntry) FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.user.id = :userId " +
                        "AND voteEntry.date BETWEEN :dateStart AND :dateEnd"),
        @NamedQuery(name = VoteEntry.GET_RATING_PAIRS,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry " +
                    "GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.GET_RATING_PAIRS_BY_DATE,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.date = :date " +
                    "GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.GET_RATING_PAIRS_BY_DATE_BETWEEN,
            query = "SELECT new sergesv.rvs.model.RatingPair(voteEntry.restaurant, " +
                                                            "COUNT (voteEntry))" +
                    "FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.date BETWEEN :dateStart AND :dateEnd " +
                    "GROUP BY voteEntry.restaurant"),
        @NamedQuery(name = VoteEntry.DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE,
            query = "DELETE FROM VoteEntry voteEntry " +
                    "WHERE voteEntry.user.id = :userId " +
                        "AND voteEntry.restaurant.id = :restaurantId " +
                        "AND voteEntry.date = :date")
})
@Table(name = "vote_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VoteEntry implements EntityWithId {
    public static final String FIND_ALL_BY_USER_ID = "VoteEntry.findAllByUserId";
    public static final String FIND_ALL_BY_USER_ID_AND_DATE_BETWEEN =
            "VoteEntry.findAllByUserIdAndDateBetween";
    public static final String GET_RATING_PAIRS = "VoteEntry.getRatingPairs";
    public static final String GET_RATING_PAIRS_BY_DATE = "VoteEntry.getRatingPairsByDate";
    public static final String GET_RATING_PAIRS_BY_DATE_BETWEEN =
            "VoteEntry.getRatingPairsByDateBetween";
    public static final String DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE =
            "VoteEntry.deleteByUserIdAndRestaurantIdAndDate";
    public static final String COUNT_ALL_BY_USER_ID = "VoteEntry.countAllByUserId";
    public static final String COUNT_ALL_BY_USER_ID_AND_DATE_BETWEEN =
            "VoteEntry.countAllByUserIdAndDateBetween";

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
