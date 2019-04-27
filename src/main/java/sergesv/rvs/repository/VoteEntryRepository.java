package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.VoteEntry;

import java.time.LocalDate;

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
}
