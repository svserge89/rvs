package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @EntityGraph(Restaurant.GRAPH_WITH_MENU_ENTRIES)
    Page<Restaurant> findAllByMenuEntryDateOrMenuEntryDateIsNull(LocalDate date,
                                                                 Pageable pageable);
    @EntityGraph(Restaurant.GRAPH_WITH_MENU_ENTRIES)
    Optional<Restaurant> findByIdAndMenuEntryDateOrMenuEntryDateIsNull(Long id, LocalDate date);
}
