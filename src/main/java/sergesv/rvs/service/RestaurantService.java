package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final VoteEntryRepository voteEntryRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public PageTo<RestaurantTo> getAll(Pageable pageable) {
        log.debug("getAll params: pageable=\"{}\"", pageable);

        return toRestaurantTos(restaurantRepository.findAll(pageable),
                false, false);
    }

    public PageTo<RestaurantTo> getAllWithRating(Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\"", pageable);

        return toRestaurantTos(restaurantRepository.findAllWithRating(pageable),
                false, true);
    }

    public PageTo<RestaurantTo> getAllWithRating(LocalDate date, Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\", date={}", pageable, date);

        return toRestaurantTos(restaurantRepository.findAllWithRatingBy(date, pageable),
                false, true);
    }

    public PageTo<RestaurantTo> getAllWithRating(LocalDate dateStart, LocalDate dateEnd,
                                               Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\", dateStart={}, dateEnd={}", pageable,
                dateStart, dateEnd);

        return toRestaurantTos(restaurantRepository.findAllWithRatingBetween(dateStart, dateEnd,
                pageable), false, true);
    }

    public PageTo<RestaurantTo> getAllWithMenu(LocalDate menuDate, Pageable pageable) {
        log.debug("getAllWithMenu params: pageable=\"{}\", menuDate={}", pageable, menuDate);

        return toRestaurantTos(restaurantRepository.findAllWithMenu(
                menuDate, pageable), true, false);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}", pageable,
                menuDate);

        return toRestaurantTos(restaurantRepository.findAllWithMenuAndRating(
                menuDate, pageable), true, true);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDate,
                                                      Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}, " +
                        "ratingDate={}", pageable, menuDate, ratingDate);

        return toRestaurantTos(restaurantRepository.findAllWithMenuAndRatingBy(menuDate, ratingDate,
                pageable), true, true);
    }

    public PageTo<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate,
                                                        LocalDate ratingDateStart,
                                                        LocalDate ratingDateEnd,
                                                        Pageable pageable) {
        log.debug("getAllWithMenuAndRating params: pageable=\"{}\", menuDate={}, " +
                        "ratingDateStart={}, ratingDateEnd={}", pageable, menuDate, ratingDateStart,
                ratingDateEnd);

        return toRestaurantTos(restaurantRepository.findAllWithMenuAndRatingBetween(
                menuDate, ratingDateStart, ratingDateEnd, pageable), true, true);
    }

    public RestaurantTo getOne(long id) {
        log.debug("getOne params: id={}", id);

        return toTo(restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)),
                false, false);
    }

    public RestaurantTo getOneWithRating(long id) {
        log.debug("getOneWithRating params: id={}", id);

        Restaurant restaurant = restaurantRepository.findByIdWithRating(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, true);
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        log.debug("getOneWithRating params: id={}, date={}", id, date);

        Restaurant restaurant = restaurantRepository.findByIdWithRatingBy(id, date)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, true);
    }

    public RestaurantTo getOneWithRating(long id, LocalDate dateStart, LocalDate dateEnd) {
        log.debug("getOneWithRating params: id={}, dateStart={}, dateEnd={}", id, dateStart,
                dateEnd);

        Restaurant restaurant = restaurantRepository.findByIdWithRatingBetween(id, dateStart,
                dateEnd).orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, true);
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate menuDate) {
        log.debug("getOneWithMenu params: id={}, menuDate={}", id, menuDate);

        return toTo(restaurantRepository.findByIdWithMenu(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)),
                true, false);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}", id, menuDate);

        Restaurant restaurant = restaurantRepository.findByIdWithMenuAndRating(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, true);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate, LocalDate ratingDate) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}, ratingDate={}", id, menuDate,
                ratingDate);

        Restaurant restaurant = restaurantRepository.findByIdWithMenuAndRatingBy(
                id, menuDate, ratingDate).orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, true);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate,
                                                LocalDate ratingDateStart,
                                                LocalDate ratingDateEnd) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}, ratingDateStart={}, " +
                        "ratingDateEnd={}", id, menuDate, ratingDateStart, ratingDateEnd);

        Restaurant restaurant = restaurantRepository.findByIdWithMenuAndRatingBetween(
                id, menuDate, ratingDateStart, ratingDateEnd)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, true);
    }

    public int getRating(long restaurantId) {
        log.debug("getRating params: restaurantId={}", restaurantId);

        return voteEntryRepository.countByRestaurantId(restaurantId);
    }

    public int getRating(long restaurantId, LocalDate date) {
        log.debug("getRating params: restaurantId={}, date={}", restaurantId, date);

        return voteEntryRepository.countByRestaurantIdAndDateEquals(restaurantId, date);
    }

    public int getRating(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
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
                false, false);
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
                                                        boolean withMenu, boolean rating) {
        return toTo(restaurantPage, page -> page.get()
                .map(restaurant -> toTo(restaurant, withMenu, rating))
                .collect(Collectors.toList()));
    }
}
