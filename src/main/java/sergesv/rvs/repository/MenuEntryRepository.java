package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.MenuEntry;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MenuEntryRepository extends JpaRepository<MenuEntry, Long> {
    Page<MenuEntry> findAllByRestaurantIdAndDate(Long restaurantId, LocalDate date,
                                                 Pageable pageable);

    Optional<MenuEntry> findByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteByIdAndRestaurantId(Long id, Long restaurantId);

    void deleteAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    void deleteAllByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                               LocalDate dateEnd);
}
