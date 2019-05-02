package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(name = Restaurant.FIND_ALL_WITH_MENU, countName = Restaurant.COUNT_ALL_WITH_MENU)
    Page<Restaurant> findAllWithMenu(LocalDate menuDate, Pageable pageable);

    @Query(name = Restaurant.FIND_ONE_WITH_MENU)
    Optional<Restaurant> findByIdWithMenu(Long id, LocalDate menuDate);
}
