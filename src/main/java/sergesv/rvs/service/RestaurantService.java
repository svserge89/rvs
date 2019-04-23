package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final VoteEntryRepository voteEntryRepository;

    public List<RestaurantTo> getAll() {
        return null;
    }

    public List<RestaurantTo> getAllWithRating() {
        return null;
    }

    public List<RestaurantTo> getAllWithRating(LocalDate date) {
        return null;
    }

    public List<RestaurantTo> getAllWithRating(LocalDate dateStart, LocalDate dateEnd) {
        return null;
    }

    public List<RestaurantTo> getAllWithMenu(LocalDate menuDate) {
        return null;
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate) {
        return null;
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDate) {
        return null;
    }

    public List<RestaurantTo> getAllWithMenuAndRating(LocalDate menuDate, LocalDate ratingDateStart,
                                                      LocalDate ratingDateEnd) {
        return null;
    }

    public RestaurantTo getOne(long id) {
        return null;
    }

    public RestaurantTo getOneWithRating(long id) {
        return null;
    }

    public RestaurantTo getOneWithRating(long id, LocalDate date) {
        return null;
    }

    public RestaurantTo getOneWithRating(long id, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public RestaurantTo getOneWithMenu(long id, LocalDate menuDate) {
        return null;
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate) {
        return null;
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate, LocalDate ratingDate) {
        return null;
    }

    public RestaurantTo getOneWithMenuAndRating(long id, LocalDate menuDate,
                                                LocalDate ratingDateStart,
                                                LocalDate ratingDateEnd) {
        return null;
    }

    public long getRating(long restaurantId) {
        return 0;
    }

    public long getRating(long restaurantId, LocalDate date) {
        return 0;
    }

    public long getRating(long restaurantId, LocalDate dateStart, LocalDate dateEnd) {
        return 0;
    }

    @Transactional
    public RestaurantTo create(RestaurantTo restaurantTo) {
        return null;
    }

    @Transactional
    public void update(long id, RestaurantTo restaurantTo) {
    }

    @Transactional
    public void delete(long id) {
    }

    @Transactional
    public void deleteAll() {
    }
}
