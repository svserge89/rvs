package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RatingPair;
import sergesv.rvs.model.VoteEntry;

import java.time.LocalDate;
import java.util.Optional;
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

    @Query(name = VoteEntry.FIND_ALL_BY_USER_ID, countName = VoteEntry.COUNT_ALL_BY_USER_ID)
    Page<VoteEntry> findAllByUserId(Long userId, Pageable pageable);

    @Query(name = VoteEntry.FIND_ALL_BY_USER_ID_AND_DATE_BETWEEN,
            countName = VoteEntry.COUNT_ALL_BY_USER_ID_AND_DATE_BETWEEN)
    Page<VoteEntry> findAllByUserIdAndDateBetween(Long userId, LocalDate dateStart,
                                                  LocalDate dateEnd, Pageable pageable);

    Optional<VoteEntry> findByUserIdAndDate(Long userId, LocalDate date);

    @Modifying
    @Query(name = VoteEntry.DELETE_BY_USER_ID_AND_RESTAURANT_ID_AND_DATE)
    void deleteByUserIdAndRestaurantIdAndDate(Long userId, Long restaurantId, LocalDate date);
}
