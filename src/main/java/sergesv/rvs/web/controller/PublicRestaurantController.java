package sergesv.rvs.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.service.MenuEntryService;
import sergesv.rvs.service.RestaurantService;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.DateTimeUtil.*;
import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.web.ControllerUtil.*;

@RestController
@RequestMapping("/api/public/restaurants")
@RequiredArgsConstructor
public class PublicRestaurantController {
    private final RestaurantService restaurantService;
    private final MenuEntryService menuEntryService;
    private final RvsPropertyResolver propertyResolver;

    @GetMapping
    public PageTo<RestaurantTo> getAll(@RequestParam(required = false) boolean rating,
                                       @RequestParam(required = false) boolean menu,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size,
                                       @RequestParam(required = false) String sort) {
        Pageable pageable = resolvePageable(page, size, resolveSort(sort, menu, rating,
                propertyResolver), propertyResolver.getRestaurantPageSize());

        switch (resolveParams(rating, menu, ratingDate, ratingDateStart, ratingDateEnd)) {
            case MENU:
                return restaurantService.getAllWithMenu(
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), pageable);
            case RATING:
                return restaurantService.getAllWithRating(pageable);
            case RATING_BY_DATE:
                return restaurantService.getAllWithRating(ratingDate, pageable);
            case RATING_BETWEEN_DATES:
                return restaurantService.getAllWithRating(
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE), pageable);
            case MENU_AND_RATING:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), pageable);
            case MENU_AND_RATING_BY_DATE:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), ratingDate,
                        pageable);
            case MENU_AND_RATING_BETWEEN_DATES:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()),
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE), pageable);
            default:
                return restaurantService.getAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo getOne(@PathVariable long id,
                               @RequestParam(required = false) boolean rating,
                               @RequestParam(required = false) boolean menu,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate,
                               @RequestParam(required = false) String sort) {
        Sort sorter = menu ? getSort(sort, SINGLE_RESTAURANT_MENU_ENTRY_PARAMS)
                .orElse(propertyResolver.getSortSingleRestaurantMenuEntry()) : Sort.unsorted();

        switch (resolveParams(rating, menu, ratingDate, ratingDateStart, ratingDateEnd)) {
            case MENU:
                return restaurantService.getOneWithMenu(id,
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), sorter);
            case RATING:
                return restaurantService.getOneWithRating(id);
            case RATING_BY_DATE:
                return restaurantService.getOneWithRating(id, ratingDate);
            case RATING_BETWEEN_DATES:
                return restaurantService.getOneWithRating(id,
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE));
            case MENU_AND_RATING:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), sorter);
            case MENU_AND_RATING_BY_DATE:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()), ratingDate, sorter);
            case MENU_AND_RATING_BETWEEN_DATES:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(menuDate).orElse(getCurrentDate()),
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE), sorter);
            default:
                return restaurantService.getOne(id);
        }
    }

    @GetMapping("/{restaurantId}/menu")
    public PageTo<MenuEntryTo> getMenu(@PathVariable long restaurantId,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size,
                                       @RequestParam(required = false) String sort) {
        Pageable pageable = resolvePageable(page, size,
                getSort(sort, MENU_ENTRY_PARAMS).orElse(propertyResolver.getSortMenuEntry()),
                propertyResolver.getMenuEntryPageSize());

        switch (resolveParams(date, dateStart, dateEnd)) {
            case BY_DATE:
                return menuEntryService.getAllByRestaurant(restaurantId, date, pageable);
            case BETWEEN_DATES:
                return menuEntryService.getAllByRestaurant(restaurantId,
                        Optional.ofNullable(dateStart).orElse(MIN_DATE),
                        Optional.ofNullable(dateEnd).orElse(MAX_DATE), pageable);
            default:
                return menuEntryService.getAllByRestaurant(restaurantId, pageable);
        }
    }

    @GetMapping("/{restaurantId}/menu/{id}")
    public MenuEntryTo getMenuEntry(@PathVariable long restaurantId, @PathVariable long id) {
        return menuEntryService.getOneByRestaurant(id, restaurantId);
    }

    @GetMapping("/{restaurantId}/rating")
    public int getRating(@PathVariable long restaurantId,
                         @RequestParam(required = false)
                         @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                         @RequestParam(required = false)
                         @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
                         @RequestParam(required = false)
                         @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
        switch (resolveParams(date, dateStart, dateEnd)) {
            case BY_DATE:
                return restaurantService.getRating(restaurantId, date);
            case BETWEEN_DATES:
                return restaurantService.getRating(restaurantId,
                        Optional.ofNullable(dateStart).orElse(MIN_DATE),
                        Optional.ofNullable(dateEnd).orElse(MAX_DATE));
            default:
                return restaurantService.getRating(restaurantId);
        }
    }
}
