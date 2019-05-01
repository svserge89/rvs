package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.repository.MenuEntryRepository;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuEntryService {
    private final MenuEntryRepository menuEntryRepository;
    private final RestaurantRepository restaurantRepository;

    public List<MenuEntryTo> getAllByRestaurant(long restaurantId, LocalDate date) {
        return menuEntryRepository.findAllByRestaurantIdAndDate(restaurantId, date).stream()
                .map(ToUtil::toTo)
                .collect(Collectors.toList());
    }

    public MenuEntryTo getOneByRestaurant(long id, long restaurantId) {
        return toTo(menuEntryRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(menuEntryNotFoundSupplier(id, restaurantId)));
    }

    @Transactional
    public MenuEntryTo create(MenuEntryTo menuEntryTo, long restaurantId) {
        checkException(restaurantRepository.existsById(restaurantId),
                restaurantNotFoundSupplier(restaurantId));

        Restaurant restaurant = restaurantRepository.getOne(restaurantId);

        return toTo(checkException(() -> menuEntryRepository.save(toModel(menuEntryTo, restaurant)),
                menuEntryAlreadyExistsSupplier()));
    }

    @Transactional
    public void update(long id, MenuEntryTo menuEntryTo, long restaurantId) {
        checkException(restaurantRepository.existsById(restaurantId),
                restaurantNotFoundSupplier(restaurantId));
        checkException(menuEntryRepository.existsByIdAndRestaurantId(id, restaurantId),
                menuEntryNotFoundSupplier(id, restaurantId));

        Restaurant restaurant = restaurantRepository.getOne(restaurantId);

        checkException(() -> menuEntryRepository.save(toModel(id, menuEntryTo, restaurant)),
                menuEntryAlreadyExistsSupplier());
    }

    @Transactional
    public void deleteByRestaurant(long id, long restaurantId) {
        menuEntryRepository.deleteByIdAndRestaurantId(id, restaurantId);
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate date) {
        menuEntryRepository.deleteAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
        menuEntryRepository.deleteAllByRestaurantIdAndDateBetween(restaurantId, dateStart,
                dateEnd);
    }
}
