package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.repository.MenuEntryRepository;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuEntryService {
    private final MenuEntryRepository menuEntryRepository;
    private final RestaurantRepository restaurantRepository;

    public List<MenuEntryTo> getAllByRestaurant(long restaurantId, LocalDate date) {
        return null;
    }

    public MenuEntryTo getOneByRestaurant(long id, long restaurantId) {
        return null;
    }

    @Transactional
    public MenuEntryTo create(MenuEntryTo menuEntryTo, long restaurantId) {
        return null;
    }

    @Transactional
    public void update(long id, MenuEntryTo menuEntryTo, long restaurantId) {
    }

    @Transactional
    public void deleteByRestaurant(long id, long restaurantId) {
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate date) {
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
    }
}
