package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RatingPair;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
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

    public List<RestaurantTo> getAll(Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAll(pageable).getContent(),
                false);
    }

    public List<RestaurantTo> getAllWithRating(Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAll(pageable).getContent(),
                voteEntryRepository.getRatingPairs(), false);
    }

    public List<RestaurantTo> getAllWithRating(LocalDate date, Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAll(pageable).getContent(),
                voteEntryRepository.getRatingPairsByDate(date), false);
    }

    public List<RestaurantTo> getAllWithRating(LocalDate dateStart, LocalDate dateEnd,
                                               Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAll(pageable).getContent(),
                voteEntryRepository.getRatingPairsByDateBetween(dateStart, dateEnd), false);
    }

    public List<RestaurantTo> getAllWithMenu(LocalDate menuDate, Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate,
                pageable).getContent(), true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate,
                pageable).getContent(), voteEntryRepository.getRatingPairs(),
                true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDate,
                                                      Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate,
                pageable).getContent(),
                voteEntryRepository.getRatingPairsByDate(ratingDate), true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDateStart,
                                                      LocalDate ratingDateEnd, Pageable pageable) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate,
                pageable).getContent(),
                voteEntryRepository.getRatingPairsByDateBetween(ratingDateStart, ratingDateEnd),
                true);
    }

    public RestaurantTo getOne(long id) {
        return toTo(restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)), false);
    }

    public RestaurantTo getOneWithRating(long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, date));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate startDate, LocalDate endDate) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, false, voteEntryRepository.countByRestaurantAndDateBetween(
                restaurant, startDate, endDate));
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate menuDate) {
        return toTo(restaurantRepository.findByIdWithMenu(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id)), true);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate) {
        Restaurant restaurant = restaurantRepository.findByIdWithMenu(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate, LocalDate ratingDate) {
        Restaurant restaurant = restaurantRepository.findByIdWithMenu(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, ratingDate));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate,
                                                LocalDate ratingDateStart,
                                                LocalDate ratingDateEnd) {
        Restaurant restaurant = restaurantRepository.findByIdWithMenu(id, menuDate)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        return toTo(restaurant, true, voteEntryRepository.countByRestaurantAndDateBetween(
                restaurant, ratingDateStart, ratingDateEnd));
    }

    public long getRating(long restaurantId) {
        return voteEntryRepository.countByRestaurantId(restaurantId);
    }

    public long getRating(long restaurantId, LocalDate date) {
        return voteEntryRepository.countByRestaurantIdAndDateEquals(restaurantId, date);
    }

    public long getRating(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
        return voteEntryRepository.countByRestaurantIdAndDateBetween(restaurantId, dateStart,
                dateEnd);
    }

    @Transactional
    public RestaurantTo create(RestaurantTo restaurantTo) {
        return toTo(checkExistsException(
                () -> restaurantRepository.save(toModel(restaurantTo)), Restaurant.class),
                false);
    }

    @Transactional
    public void update(long id, RestaurantTo restaurantTo) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(Restaurant.class, id));

        Optional.ofNullable(restaurantTo.getName()).ifPresent(restaurant::setName);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void delete(long id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        restaurantRepository.deleteAll();
    }

    private static List<RestaurantTo> toRestaurantTos(List<Restaurant> restaurants,
                                                      Set<RatingPair> ratingPairs,
                                                      boolean withMenu) {
        var ratingMap = ratingPairs.stream()
                .collect(Collectors.toMap(RatingPair::getRestaurant, RatingPair::getRating));

        return restaurants.stream()
                .map(restaurant -> toTo(restaurant, withMenu,
                        ratingMap.getOrDefault(restaurant, DEFAULT_RATING)))
                .collect(Collectors.toList());
    }

    private static List<RestaurantTo> toRestaurantTos(List<Restaurant> restaurants,
                                                      boolean withMenu) {
        return restaurants.stream()
                .map(restaurant -> toTo(restaurant, withMenu))
                .collect(Collectors.toList());
    }
}
