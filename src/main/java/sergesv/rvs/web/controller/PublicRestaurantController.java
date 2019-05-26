package sergesv.rvs.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.service.MenuEntryService;
import sergesv.rvs.service.RestaurantService;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Optional;

import static sergesv.rvs.util.DateTimeUtil.*;
import static sergesv.rvs.util.web.ControllerUtil.*;

@RestController
@RequestMapping("/api/public/restaurants")
@RequiredArgsConstructor
public class PublicRestaurantController {
    private final RestaurantService restaurantService;
    private final MenuEntryService menuEntryService;
    private final RvsPropertyResolver propertyResolver;

    @GetMapping
    public Page<RestaurantTo> getAll(@RequestParam(required = false) boolean rating,
                                     @RequestParam(required = false) LocalDate date,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size,
                                     @RequestParam(required = false) Sort sort) {
        var pageable = resolvePageable(page, size, sort, propertyResolver.getRestaurantPageSize(),
                rating ? propertyResolver.getSortRestaurantWithRating() :
                        propertyResolver.getSortRestaurant());

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
                               @RequestParam(required = false) Sort sort) {
        var sortOptional = Optional.ofNullable(sort);
        var dateOptional = Optional.ofNullable(date);

        if (menu && rating) {
            return restaurantService.getOneWithMenuAndRating(id,
                    dateOptional.orElse(getCurrentDate()),
                    sortOptional.orElse(propertyResolver.getSortSingleRestaurantMenuEntry()));
        } else if (menu) {
            return restaurantService.getOneWithMenu(id, dateOptional.orElse(getCurrentDate()),
                    sortOptional.orElse(propertyResolver.getSortSingleRestaurantMenuEntry()));
        } else if (rating) {
            return restaurantService.getOneWithRating(id, dateOptional.orElse(getCurrentDate()));
        } else {
            return restaurantService.getOne(id);
        }
    }

    @GetMapping("/{restaurantId}/menu")
    public Page<MenuEntryTo> getMenu(@PathVariable long restaurantId,
                                     @RequestParam(required = false) LocalDate date,
                                     @RequestParam(required = false) LocalDate dateStart,
                                     @RequestParam(required = false) LocalDate dateEnd,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size,
                                     @RequestParam(required = false) Sort sort) {
        var pageable = resolvePageable(page, size, sort,
                propertyResolver.getMenuEntryPageSize(), propertyResolver.getSortMenuEntry());
        var dateOptional = Optional.ofNullable(date);
        var dateStartOptional = Optional.ofNullable(dateStart);
        var dateEndOptional = Optional.ofNullable(dateEnd);

        if (dateStartOptional.isPresent() || dateEndOptional.isPresent()) {
            return menuEntryService.getAllByRestaurant(restaurantId,
                    dateStartOptional.orElse(MIN_DATE), dateEndOptional.orElse(MAX_DATE), pageable);
        } else if (dateOptional.isPresent()) {
            return menuEntryService.getAllByRestaurant(restaurantId, dateOptional.get(), pageable);
        } else {
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
