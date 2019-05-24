package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RestaurantWithRating;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(value = "SELECT new sergesv.rvs.model.RestaurantWithRating(restaurant.id, " +
                            "restaurant.name, COUNT (voteEntry)) " +
                    "FROM Restaurant restaurant LEFT JOIN restaurant.voteEntry voteEntry " +
                            "ON voteEntry.date = :date " +
                    "GROUP BY restaurant",
            countQuery = "SELECT COUNT (restaurant) FROM Restaurant restaurant")
    Page<RestaurantWithRating> findAllWithRating(LocalDate date, Pageable pageable);

    @EntityGraph(attributePaths = "voteEntry")
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :date " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithRating(Long id, LocalDate date);

    @EntityGraph(attributePaths = "menuEntry")
    @Query("SELECT DISTINCT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :date " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenu(Long id, LocalDate date, Sort sort);

    @EntityGraph(attributePaths = {"menuEntry", "voteEntry"})
    @Query("SELECT restaurant FROM Restaurant restaurant " +
                "LEFT JOIN restaurant.menuEntry menuEntry ON menuEntry.date = :date " +
                "LEFT JOIN restaurant.voteEntry voteEntry ON voteEntry.date = :date " +
            "WHERE restaurant.id = :id")
    Optional<Restaurant> findByIdWithMenuAndRating(Long id, LocalDate date, Sort sort);

    @Modifying
    @Query("DELETE FROM Restaurant restaurant WHERE restaurant.id = :id")
    Integer delete(Long id);
}
