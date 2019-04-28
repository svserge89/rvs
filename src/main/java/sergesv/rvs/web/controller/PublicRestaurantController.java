package sergesv.rvs.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.service.MenuEntryService;
import sergesv.rvs.service.RestaurantService;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.DateUtil.MAX_DATE;
import static sergesv.rvs.util.DateUtil.MIN_DATE;
import static sergesv.rvs.util.web.ControllerUtil.resolveParams;

@RestController
@RequestMapping("/api/public/restaurants")
@RequiredArgsConstructor
public class PublicRestaurantController {
    private final RestaurantService restaurantService;
    private final MenuEntryService menuEntryService;

    @GetMapping
    public List<RestaurantTo> getAll(
            @RequestParam(required = false) Boolean rating,
            @RequestParam(required = false) Boolean menu,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        switch (resolveParams(rating, menu, ratingDate, ratingDateStart, ratingDateEnd)) {
            case MENU:
                return restaurantService.getAllWithMenu(
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()));
            case RATING:
                return restaurantService.getAllWithRating();
            case RATING_BY_DATE:
                return restaurantService.getAllWithRating(ratingDate);
            case RATING_BETWEEN_DATES:
                return restaurantService.getAllWithRating(
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE));
            case MENU_AND_RATING:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()));
            case MENU_AND_RATING_BY_DATE:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()), ratingDate);
            case MENU_AND_RATING_BETWEEN_DATES:
                return restaurantService.getAllWithMenuAndRating(
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()),
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE));
            default:
                return restaurantService.getAll();
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo getOne(
            @PathVariable long id,
            @RequestParam(required = false) Boolean rating,
            @RequestParam(required = false) Boolean menu,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        switch (resolveParams(rating, menu, ratingDate, ratingDateStart, ratingDateEnd)) {
            case MENU:
                return restaurantService.getOneWithMenu(id,
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()));
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
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()));
            case MENU_AND_RATING_BY_DATE:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()), ratingDate);
            case MENU_AND_RATING_BETWEEN_DATES:
                return restaurantService.getOneWithMenuAndRating(id,
                        Optional.ofNullable(menuDate).orElse(LocalDate.now()),
                        Optional.ofNullable(ratingDateStart).orElse(MIN_DATE),
                        Optional.ofNullable(ratingDateEnd).orElse(MAX_DATE));
            default:
                return restaurantService.getOne(id);
        }
    }

    @GetMapping("/{restaurantId}/menu")
    public List<MenuEntryTo> getMenu(@PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        return menuEntryService.getAllByRestaurant(restaurantId,
                Optional.ofNullable(menuDate).orElse(LocalDate.now()));
    }

    @GetMapping("/{restaurantId}/menu/{id}")
    public MenuEntryTo getMenuEntry(@PathVariable long restaurantId, @PathVariable long id) {
        return menuEntryService.getOneByRestaurant(id, restaurantId);
    }

    @GetMapping("/{restaurantId}/rating")
    public Long getRating(@PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
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
