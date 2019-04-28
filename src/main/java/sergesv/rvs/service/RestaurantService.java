package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.RatingPair;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
    private static final long DEFAULT_RATING = 0L;

    private final RestaurantRepository restaurantRepository;
    private final VoteEntryRepository voteEntryRepository;

    public List<RestaurantTo> getAll() {
        return toRestaurantTos(restaurantRepository.findAll(), false);
    }

    public List<RestaurantTo> getAllWithRating() {
        return toRestaurantTos(restaurantRepository.findAll(), voteEntryRepository.getRatingPairs(),
                false);
    }

    public List<RestaurantTo> getAllWithRating(LocalDate date) {
        return toRestaurantTos(restaurantRepository.findAll(),
                voteEntryRepository.getRatingPairsByDate(date), false);
    }

    public List<RestaurantTo> getAllWithRating(LocalDate dateStart, LocalDate dateEnd) {
        return toRestaurantTos(restaurantRepository.findAll(),
                voteEntryRepository.getRatingPairsByDateBetween(dateStart, dateEnd), false);
    }

    public List<RestaurantTo> getAllWithMenu(LocalDate menuDate) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate), true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate),
                voteEntryRepository.getRatingPairs(), true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDate) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate),
                voteEntryRepository.getRatingPairsByDate(ratingDate), true);
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDateStart,
                                                      LocalDate ratingDateEnd) {
        return toRestaurantTos(restaurantRepository.findAllWithMenu(menuDate),
                voteEntryRepository.getRatingPairsByDateBetween(ratingDateStart, ratingDateEnd),
                true);
    }

    public RestaurantTo getOne(long id) {
        return toTo(restaurantRepository.getOne(id), false);
    }

    public RestaurantTo getOneWithRating(long id) {
        Restaurant restaurant = restaurantRepository.getOne(id);

        return toTo(restaurant, false, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        Restaurant restaurant = restaurantRepository.getOne(id);

        return toTo(restaurant, false,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, date));
    }

    public RestaurantTo getOneWithRating(long id, LocalDate startDate, LocalDate endDate) {
        Restaurant restaurant = restaurantRepository.getOne(id);

        return toTo(restaurant, false, voteEntryRepository.countByRestaurantAndDateBetween(
                restaurant, startDate, endDate));
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate menuDate) {
        return toTo(restaurantRepository.getOneWithMenu(id, menuDate), true);
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate) {
        Restaurant restaurant = restaurantRepository.getOneWithMenu(id, menuDate);

        return toTo(restaurant, true, voteEntryRepository.countByRestaurant(restaurant));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate, LocalDate ratingDate) {
        Restaurant restaurant = restaurantRepository.getOneWithMenu(id, menuDate);

        return toTo(restaurant, true,
                voteEntryRepository.countByRestaurantAndDateEquals(restaurant, ratingDate));
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate,
                                                LocalDate ratingDateStart,
                                                LocalDate ratingDateEnd) {
        Restaurant restaurant = restaurantRepository.getOneWithMenu(id, menuDate);

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
        return toTo(restaurantRepository.save(toModel(restaurantTo)), false);
    }

    @Transactional
    public void update(long id, RestaurantTo restaurantTo) {
        Restaurant restaurant = toModel(restaurantTo);

        restaurant.setId(id);
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
