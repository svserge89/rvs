package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.MenuEntry;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MenuEntryRepository extends JpaRepository<MenuEntry, Long> {
    @Query("SELECT menuEntry FROM MenuEntry menuEntry " +
            "WHERE menuEntry.restaurant.id = :restaurantId")
    Page<MenuEntry> findAllByRestaurantId(Long restaurantId, Pageable pageable);

    @Query("SELECT menuEntry FROM MenuEntry menuEntry " +
            "WHERE menuEntry.restaurant.id = :restaurantId AND menuEntry.date = :date")
    Page<MenuEntry> findAllByRestaurantIdAndDate(Long restaurantId, LocalDate date,
                                                 Pageable pageable);

    @Query("SELECT menuEntry FROM MenuEntry menuEntry " +
            "WHERE menuEntry.restaurant.id = :restaurantId " +
                "AND menuEntry.date BETWEEN :dateStart AND :dateEnd")
    Page<MenuEntry> findAllByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                                        LocalDate dateEnd, Pageable pageable);

    @Query("SELECT menuEntry FROM MenuEntry menuEntry WHERE menuEntry.id = :id " +
                "AND menuEntry.restaurant.id = :restaurantId")
    Optional<MenuEntry> findByIdAndRestaurantId(Long id, Long restaurantId);

    @Modifying
    @Query("DELETE FROM MenuEntry menuEntry WHERE menuEntry.id = :id " +
                "AND menuEntry.restaurant.id = :restaurantId")
    void deleteByIdAndRestaurantId(Long id, Long restaurantId);

    @Modifying
    @Query("DELETE FROM MenuEntry menuEntry WHERE menuEntry.restaurant.id = :restaurantId " +
                "AND menuEntry.date = :date")
    void deleteAllByRestaurantIdAndDate(Long restaurantId, LocalDate date);

    @Modifying
    @Query("DELETE FROM MenuEntry menuEntry WHERE menuEntry.restaurant.id = :restaurantId " +
                "AND menuEntry.date BETWEEN :dateStart AND :dateEnd")
    void deleteAllByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                               LocalDate dateEnd);
}
