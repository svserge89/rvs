package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant")
    Page<Restaurant> findAllWithRating(Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :ratingDate")
    Page<Restaurant> findAllWithRatingBy(LocalDate ratingDate, Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.voteEntry voteEntry " +
                    "ON voteEntry.date BETWEEN :ratingDateStart AND :ratingDateEnd")
    Page<Restaurant> findAllWithRatingBetween(LocalDate ratingDateStart, LocalDate ratingDateEnd,
                                              Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate")
    Page<Restaurant> findAllWithMenu(LocalDate menuDate, Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
                "LEFT JOIN restaurant.voteEntry voteEntry")
    Page<Restaurant> findAllWithMenuAndRating(LocalDate menuDate, Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :ratingDate")
    Page<Restaurant> findAllWithMenuAndRatingBy(LocalDate menuDate, LocalDate ratingDate,
                                                Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
                "LEFT JOIN restaurant.voteEntry voteEntry " +
                    "ON voteEntry.date BETWEEN :ratingDateStart AND :ratingDateEnd")
    Page<Restaurant> findAllWithMenuAndRatingBetween(LocalDate menuDate, LocalDate ratingDateStart,
                                                     LocalDate ratingDateEnd, Pageable pageable);

    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithRating(Long id);

    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :ratingDate " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithRatingBy(Long id, LocalDate ratingDate);

    @EntityGraph(Restaurant.GRAPH_WITH_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.voteEntry voteEntry " +
                    "ON voteEntry.date BETWEEN :ratingDateStart AND :ratingDateEnd " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithRatingBetween(Long id, LocalDate ratingDateStart,
                                                   LocalDate ratingDateEnd);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU)
    @Query("SELECT DISTINCT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenu(Long id, LocalDate menuDate, Sort sort);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenuAndRating(Long id, LocalDate menuDate, Sort sort);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :ratingDate " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenuAndRatingBy(Long id, LocalDate menuDate,
                                                     LocalDate ratingDate, Sort sort);

    @EntityGraph(Restaurant.GRAPH_WITH_MENU_AND_VOTE)
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :menuDate " +
                "LEFT JOIN restaurant.voteEntry voteEntry " +
                    "ON voteEntry.date BETWEEN :ratingDateStart AND :ratingDateEnd " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenuAndRatingBetween(Long id, LocalDate menuDate,
                                                          LocalDate ratingDateStart,
                                                          LocalDate ratingDateEnd, Sort sort);
}
