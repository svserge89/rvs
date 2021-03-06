package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.MenuEntry;
import sergesv.rvs.repository.MenuEntryRepository;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;
import java.util.Optional;

import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuEntryService {
    private final MenuEntryRepository menuEntryRepository;
    private final RestaurantRepository restaurantRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Page<MenuEntryTo> getAllByRestaurant(long restaurantId, Pageable pageable) {
        log.debug("getAllByRestaurant params: pageable=\"{}\", restaurantId={}", pageable,
                restaurantId);

        checkMenuEntrySort(pageable);

        return menuEntryRepository.findAllByRestaurantId(restaurantId, pageable).map(ToUtil::toTo);
    }

    public Page<MenuEntryTo> getAllByRestaurant(long restaurantId, LocalDate date,
                                                Pageable pageable) {
        log.debug("getAllByRestaurant params: pageable=\"{}\", restaurantId={}, date={}", pageable,
                restaurantId, date);

        checkMenuEntrySort(pageable);

        return menuEntryRepository.findAllByRestaurantIdAndDate(restaurantId, date, pageable)
                .map(ToUtil::toTo);
    }

    public Page<MenuEntryTo> getAllByRestaurant(long restaurantId, LocalDate dateStart,
                                                LocalDate dateEnd, Pageable pageable) {
        log.debug("getAllByRestaurant params: pageable=\"{}\", restaurantId={}, dateStart={}, " +
                "dateEnd={}", pageable, restaurantId, dateStart, dateEnd);

        checkMenuEntrySort(pageable);

        return menuEntryRepository.findAllByRestaurantIdAndDateBetween(restaurantId, dateStart,
                dateEnd, pageable).map(ToUtil::toTo);
    }

    public MenuEntryTo getOneByRestaurant(long id, long restaurantId) {
        log.debug("getAllByRestaurant params: id={}, restaurantId={}", id, restaurantId);

        return toTo(menuEntryRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(entityNotFoundSupplier(id, restaurantId)));
    }

    @Transactional
    public MenuEntryTo create(MenuEntryTo menuEntryTo, long restaurantId) {
        log.debug("create params: menuEntryTo={}, restaurantId={}", menuEntryTo, restaurantId);

        return toTo(checkExistsException(() -> menuEntryRepository.save(
                toModel(menuEntryTo, restaurantRepository.getOne(restaurantId))), MenuEntry.class));
    }

    @Transactional
    public void update(long id, MenuEntryTo menuEntryTo, long restaurantId) {
        log.debug("create params: id={}, menuEntryTo={}, restaurantId={}", id, menuEntryTo,
                restaurantId);

        MenuEntry menuEntry = menuEntryRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(entityNotFoundSupplier(id, restaurantId));

        Optional.ofNullable(menuEntryTo.getName()).ifPresent(menuEntry::setName);
        Optional.ofNullable(menuEntryTo.getPrice()).ifPresent(menuEntry::setPrice);
        Optional.ofNullable(menuEntryTo.getDate()).ifPresent(menuEntry::setDate);

        menuEntryRepository.save(menuEntry);
    }

    @Transactional
    public void deleteByRestaurant(long id, long restaurantId) {
        log.debug("deleteByRestaurant params: id={}, restaurantId={}", id, restaurantId);

        checkException(menuEntryRepository.deleteByIdAndRestaurantId(id, restaurantId) != 0,
                entityNotFoundSupplier(id, restaurantId));
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate date) {
        log.debug("deleteAllByRestaurant params: restaurantId={}, date={}", restaurantId, date);

        menuEntryRepository.deleteAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Transactional
    public void deleteAllByRestaurant(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
        log.debug("deleteAllByRestaurant params: restaurantId={}, dateStart={}, dateEnd={}",
                restaurantId, dateStart, dateEnd);

        menuEntryRepository.deleteAllByRestaurantIdAndDateBetween(restaurantId, dateStart,
                dateEnd);
    }

    private void checkMenuEntrySort(Pageable pageable) {
        checkSort(pageable.getSort(), NAME, PRICE, DATE);
    }
}
