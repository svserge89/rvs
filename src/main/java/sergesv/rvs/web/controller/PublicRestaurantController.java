package sergesv.rvs.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequestMapping("/api/public/restaurants")
public class PublicRestaurantController {
    @GetMapping
    List<RestaurantTo> getAll(
            @RequestParam(required = false) Boolean rating,
            @RequestParam(required = false) Boolean menu,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        return null;
    }

    @GetMapping("/{id}")
    RestaurantTo getOne(
            @PathVariable long id,
            @RequestParam(required = false) Boolean rating,
            @RequestParam(required = false) Boolean menu,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate ratingDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        return null;
    }

    @GetMapping("/{restaurantId}/menu")
    List<MenuEntryTo> getMenu(
            @PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate menuDate) {
        return null;
    }

    @GetMapping("/{restaurantId}/menu/{id}")
    MenuEntryTo getMenuEntry(@PathVariable long restaurantId, @PathVariable long id) {
        return null;
    }

    @GetMapping("/{restaurantId}/rating")
    Long getRating(
            @PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
        return null;
    }
}
