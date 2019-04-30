package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.MenuEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuEntryRepository extends JpaRepository<MenuEntry, Long> {
    List<MenuEntry> findAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    Optional<MenuEntry> findByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    void deleteAllByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                                 LocalDate dateEnd);

    boolean existsByIdAndRestaurantId(long id, long restaurantId);
}
