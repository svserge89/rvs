package sergesv.rvs.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.service.MenuEntryService;
import sergesv.rvs.service.RestaurantService;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Optional;

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
                                       @RequestParam(required = false) LocalDate date,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size,
                                       @RequestParam(required = false) String sort) {
        Pageable pageable = resolvePageable(page, size, resolveSort(sort, rating,
                propertyResolver), propertyResolver.getRestaurantPageSize());

        if (rating) {
            return restaurantService.getAllWithRating(
                    Optional.ofNullable(date).orElse(getCurrentDate()), pageable);
        } else {
            return restaurantService.getAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo getOne(@PathVariable long id,
                               @RequestParam(required = false) boolean rating,
                               @RequestParam(required = false) boolean menu,
                               @RequestParam(required = false) LocalDate date,
                               @RequestParam(required = false) String sort) {
        Sort sorter = menu ? getSort(sort, SINGLE_RESTAURANT_MENU_ENTRY_PARAMS)
                .orElse(propertyResolver.getSortSingleRestaurantMenuEntry()) : Sort.unsorted();

        switch (resolveParams(rating, menu)) {
            case MENU:
                return restaurantService.getOneWithMenu(id,
                        Optional.ofNullable(date).orElse(getCurrentDate()), sorter);
            case RATING:
                return restaurantService.getOneWithRating(id,
                        Optional.ofNullable(date).orElse(getCurrentDate()));
            case MENU_AND_RATING:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(date).orElse(getCurrentDate()), sorter);
            default:
                return restaurantService.getOne(id);
        }
    }

    @GetMapping("/{restaurantId}/menu")
    public PageTo<MenuEntryTo> getMenu(@PathVariable long restaurantId,
                                       @RequestParam(required = false) LocalDate date,
                                       @RequestParam(required = false) LocalDate dateStart,
                                       @RequestParam(required = false) LocalDate dateEnd,
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
                         @RequestParam(required = false) LocalDate date) {
        return restaurantService.getRating(restaurantId,
                Optional.ofNullable(date).orElse(getCurrentDate()));
    }
}
