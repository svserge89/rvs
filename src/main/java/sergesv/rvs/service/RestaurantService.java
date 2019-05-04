package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RatingPair;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
    private static final long DEFAULT_RATING = 0L;

    private final RestaurantRepository restaurantRepository;
    private final VoteEntryRepository voteEntryRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public PageTo<RestaurantTo> getAll(Pageable pageable) {
        log.debug("getAll params: pageable=\"{}\"", pageable);

        return toRestaurantTos(restaurantRepository.findAll(pageable), false);
    }

    public PageTo<RestaurantTo> getAllWithRating(Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\"", pageable);

        return toRestaurantTos(restaurantRepository.findAll(pageable),
                voteEntryRepository.getRatingPairs(), false);
    }

    public PageTo<RestaurantTo> getAllWithRating(LocalDate date, Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\", date={}", pageable, date);

        return toRestaurantTos(restaurantRepository.findAll(pageable),
                voteEntryRepository.getRatingPairsByDate(date), false);
    }

    public PageTo<RestaurantTo> getAllWithRating(LocalDate dateStart, LocalDate dateEnd,
                                               Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\", dateStart={}, dateEnd={}", pageable,
                dateStart, dateEnd);

        return toRestaurantTos(restaurantRepository.findAll(pageable),
                voteEntryRepository.getRatingPairsByDateBetween(dateStart, dateEnd),
                false);
    }

    public PageTo<RestaurantTo> getAllWithMenu(LocalDate menuDate, Pageable pageable) {
        log.debug("getAllWithMenu params: pageable=\"{}\", menuDate={}", pageable, menuDate);

        return toRestaurantTos(restaurantRepository.findAllByMenuEntryDateOrMenuEntryDateIsNull(
                menuDate, pageable), true);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}", pageable,
                menuDate);

        return toRestaurantTos(restaurantRepository.findAllByMenuEntryDateOrMenuEntryDateIsNull(
                menuDate, pageable), voteEntryRepository.getRatingPairs(), true);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDate,
                                                      Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}, " +
                        "ratingDate={}", pageable, menuDate, ratingDate);

        return toRestaurantTos(restaurantRepository.findAllByMenuEntryDateOrMenuEntryDateIsNull(
                menuDate, pageable), voteEntryRepository.getRatingPairsByDate(ratingDate),
                true);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate,
                                                        LocalDate ratingDateStart,
                                                        LocalDate ratingDateEnd,
                                                        Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}, " +
                        "ratingDateStart={}, ratingDateEnd={}", pageable, menuDate, ratingDateStart,
                ratingDateEnd);

        return toRestaurantTos(restaurantRepository.findAllByMenuEntryDateOrMenuEntryDateIsNull(
                menuDate, pageable), voteEntryRepository.getRatingPairsByDateBetween(
                        ratingDateStart, ratingDateEnd), true);
    }

    public RestaurantTo getOne(long id) {
        log.debug("getOne params: id={}", id);

        return toTo(restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)), false);
    }

    public RestaurantTo getOneWithRating(long id) {
        log.debug("getOneWithRating params: id={}", id);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        log.debug("getOneWithRating params: id={}, date={}", id, date);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, date));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate dateStart, LocalDate dateEnd) {
        log.debug("getOneWithRating params: id={}, dateStart={}, dateEnd={}", id, dateStart,
                dateEnd);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false,
                voteEntryRepository.countByRestaurantAndDateBetween(restaurant, dateStart,
                        dateEnd));
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate menuDate) {
        log.debug("getOneWithMenu params: id={}, menuDate={}", id, menuDate);

        return toTo(restaurantRepository.findByIdAndMenuEntryDateOrMenuEntryDateIsNull(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)), true);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}", id, menuDate);

        Restaurant restaurant = restaurantRepository.findByIdAndMenuEntryDateOrMenuEntryDateIsNull(
                id, menuDate).orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate, LocalDate ratingDate) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}, ratingDate={}", id, menuDate,
                ratingDate);

        Restaurant restaurant = restaurantRepository.findByIdAndMenuEntryDateOrMenuEntryDateIsNull(
                id, menuDate).orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, ratingDate));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate,
                                                LocalDate ratingDateStart,
                                                LocalDate ratingDateEnd) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}, ratingDateStart={}, " +
                        "ratingDateEnd={}", id, menuDate, ratingDateStart, ratingDateEnd);

        Restaurant restaurant = restaurantRepository.findByIdAndMenuEntryDateOrMenuEntryDateIsNull(
                id, menuDate).orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, voteEntryRepository.countByRestaurantAndDateBetween(
                restaurant, ratingDateStart, ratingDateEnd));
    }

    public long getRating(long restaurantId) {
        log.debug("getRating params: restaurantId={}", restaurantId);

        return voteEntryRepository.countByRestaurantId(restaurantId);
    }

    public long getRating(long restaurantId, LocalDate date) {
        log.debug("getRating params: restaurantId={}, date={}", restaurantId, date);

        return voteEntryRepository.countByRestaurantIdAndDateEquals(restaurantId, date);
    }

    public long getRating(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
        log.debug("getRating params: restaurantId={}, dateStart={}, dateEnd={}", restaurantId,
                dateStart, dateEnd);

        return voteEntryRepository.countByRestaurantIdAndDateBetween(restaurantId, dateStart,
                dateEnd);
    }

    @Transactional
    public RestaurantTo create(RestaurantTo restaurantTo) {
        log.debug("create params: restaurantTo={}", restaurantTo);

        return toTo(checkExistsException(
                () -> restaurantRepository.save(toModel(restaurantTo)), Restaurant.class),
                false);
    }

    @Transactional
    public void update(long id, RestaurantTo restaurantTo) {
        log.debug("update params: id={}, restaurantTo={}", id, restaurantTo);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        Optional.ofNullable(restaurantTo.getName()).ifPresent(restaurant::setName);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void delete(long id) {
        log.debug("delete params: id={}", id);

        restaurantRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        log.debug("deleteAll params: empty");

        restaurantRepository.deleteAll();
    }

    private static PageTo<RestaurantTo> toRestaurantTos(Page<Restaurant> restaurantPage,
                                                        Set<RatingPair> ratingPairs,
                                                        boolean withMenu) {
        return toTo(restaurantPage, page -> {
            var ratingMap = ratingPairs.stream()
                    .collect(Collectors.toMap(RatingPair::getRestaurant, RatingPair::getRating));

            return page.get()
                    .map(restaurant -> toTo(restaurant, withMenu,
                            ratingMap.getOrDefault(restaurant, DEFAULT_RATING)))
                    .collect(Collectors.toList());
        });
    }

    private static PageTo<RestaurantTo> toRestaurantTos(Page<Restaurant> restaurantPage,
                                                        boolean withMenu) {
        return toTo(restaurantPage, page -> page.get()
                .map(restaurant -> toTo(restaurant, withMenu))
                .collect(Collectors.toList()));
    }
}
