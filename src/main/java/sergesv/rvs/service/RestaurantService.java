package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RestaurantWithRating;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.util.ToUtil;
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

        return toPageTo(restaurantRepository.findAll(pageable));
    }

    public PageTo<RestaurantTo> getAllWithRating(LocalDate date, Pageable pageable) {
        log.debug("getAllWithRating params: pageable=\"{}\"", pageable);

        return toPageToWithRating(restaurantRepository.findAllWithRating(date, pageable));
    }

    public RestaurantTo getOne(long id) {
        log.debug("getOne params: id={}", id);

        return toTo(restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)),
                false, false);
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        log.debug("getOneWithRating params: id={}, date={}", id, date);

        Restaurant restaurant = restaurantRepository.findByIdWithRating(id, date)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, true);
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate date, Sort sort) {
        log.debug("getOneWithMenu params: id={}, menuDate={}, sort=\"{}\"", id, date, sort);

        return toTo(restaurantRepository.findByIdWithMenu(id, date, sort)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)),
                true, false);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate date, Sort sort) {
        log.debug("getOneWithMenuAndRating params: id={}, menuDate={}, sort=\"{}\"", id, date,
                sort);

        Restaurant restaurant = restaurantRepository.findByIdWithMenuAndRating(id, date, sort)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, true);
    }

    public int getRating(long restaurantId, LocalDate date) {
        log.debug("getRating params: restaurantId={}, date={}", restaurantId, date);

        return voteEntryRepository.countByRestaurantIdAndDateEquals(restaurantId, date);
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

        checkException(restaurantRepository.delete(id) != 0,
                entityNotFoundSupplier(Restaurant.class, id));
    }

    @Transactional
    public void deleteAll() {
        log.debug("deleteAll params: empty");

        restaurantRepository.deleteAll();
    }

    private static PageTo<RestaurantTo> toPageTo(Page<Restaurant> restaurantPage) {
        return toTo(restaurantPage, page -> page.get()
                .map(restaurant -> toTo(restaurant, false, false))
                .collect(Collectors.toList()));
    }

    private static PageTo<RestaurantTo> toPageToWithRating(
            Page<RestaurantWithRating> restaurantPage) {
        return toTo(restaurantPage, page -> page.get()
                .map(ToUtil::toTo).collect(Collectors.toList()));
    }
}
