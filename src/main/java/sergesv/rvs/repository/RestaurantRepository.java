package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(name = Restaurant.FIND_ALL_WITH_MENU)
    List<Restaurant> findAllWithMenu(LocalDate menuDate);

    @Query(name = Restaurant.GET_ONE_WITH_MENU)
    Restaurant getOneWithMenu(Long id, LocalDate menuDate);
}
