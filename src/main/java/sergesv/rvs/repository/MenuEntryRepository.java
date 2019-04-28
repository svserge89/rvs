package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.MenuEntry;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuEntryRepository extends JpaRepository<MenuEntry, Long> {
    List<MenuEntry> findAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    MenuEntry getByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    void deleteAllByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                                 LocalDate dateEnd);
}
