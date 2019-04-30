package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RatingPair;
import sergesv.rvs.model.VoteEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface VoteEntryRepository extends JpaRepository<VoteEntry, Long> {
    long countByRestaurant(Restaurant restaurant);

    long countByRestaurantAndDateEquals(Restaurant restaurant, LocalDate date);

    long countByRestaurantAndDateBetween(Restaurant restaurant, LocalDate dateStart,
                                         LocalDate dateEnd);

    long countByRestaurantId(Long restaurantId);

    long countByRestaurantIdAndDateEquals(Long restaurantId, LocalDate date);

    long countByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                           LocalDate dateEnd);

    @Query(name = VoteEntry.GET_RATING_PAIRS)
    Set<RatingPair> getRatingPairs();

    @Query(name = VoteEntry.GET_RATING_PAIRS_BY_DATE)
    Set<RatingPair> getRatingPairsByDate(LocalDate date);

    @Query(name = VoteEntry.GET_RATING_PAIRS_BY_DATE_BETWEEN)
    Set<RatingPair> getRatingPairsByDateBetween(LocalDate dateStart, LocalDate dateEnd);

    List<VoteEntry> findAllByUserId(Long userId);

    List<VoteEntry> findAllByUserIdAndDateBetween(Long userId, LocalDate dateStart,
                                                  LocalDate dateEnd);

    boolean existsByUserIdAndDate(Long userId, LocalDate date);

    @Modifying
    @Query(name = VoteEntry.DELETE_BY_USER_ID_AND_DATE)
    void deleteByUserIdAndDate(Long userId, LocalDate date);

    @Modifying
    @Query(name = VoteEntry.DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE)
    void deleteByUserIdAndRestaurantIdAndDate(Long userId, Long restaurantId, LocalDate date);
}
