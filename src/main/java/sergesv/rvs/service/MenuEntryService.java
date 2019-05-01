package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.MenuEntry;
import sergesv.rvs.repository.MenuEntryRepository;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.util.ValidationUtil;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
                .orElseThrow(ValidationUtil.entityNotFoundSupplier(id, restaurantId)));
    }

    @Transactional
    public MenuEntryTo create(MenuEntryTo menuEntryTo, long restaurantId) {
        return toTo(checkExistsException(() -> menuEntryRepository.save(
                toModel(menuEntryTo, restaurantRepository.getOne(restaurantId))), MenuEntry.class));
    }

    @Transactional
    public void update(long id, MenuEntryTo menuEntryTo, long restaurantId) {
        MenuEntry menuEntry = menuEntryRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(entityNotFoundSupplier(id, restaurantId));
        Optional.ofNullable(menuEntryTo.getName()).ifPresent(menuEntry::setName);
        Optional.ofNullable(menuEntryTo.getPrice()).ifPresent(menuEntry::setPrice);
        Optional.ofNullable(menuEntryTo.getDate()).ifPresent(menuEntry::setDate);

        menuEntryRepository.save(menuEntry);
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
