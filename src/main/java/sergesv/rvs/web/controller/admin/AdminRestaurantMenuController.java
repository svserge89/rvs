package sergesv.rvs.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.service.MenuEntryService;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.DateUtil.MAX_DATE;
import static sergesv.rvs.util.DateUtil.MIN_DATE;
import static sergesv.rvs.util.web.ControllerUtil.ParamsCondition.BETWEEN_DATES;
import static sergesv.rvs.util.web.ControllerUtil.resolveParams;

@RestController
@RequestMapping("/api/admin/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
public class AdminRestaurantMenuController {
    private final MenuEntryService menuEntryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuEntryTo create(@PathVariable long restaurantId,
                              @RequestBody MenuEntryTo menuEntryTo) {
        return menuEntryService.create(menuEntryTo, restaurantId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @PathVariable long restaurantId,
                       @RequestBody MenuEntryTo menuEntryTo) {
        menuEntryService.update(id, menuEntryTo, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @PathVariable long restaurantId) {
        menuEntryService.deleteByRestaurant(id, restaurantId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(
            @PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
        if (resolveParams(date, dateStart, dateEnd) == BETWEEN_DATES) {
            menuEntryService.deleteAllByRestaurant(restaurantId,
                    Optional.ofNullable(dateStart).orElse(MIN_DATE),
                    Optional.ofNullable(dateEnd).orElse(MAX_DATE));
        } else {
            menuEntryService.deleteAllByRestaurant(restaurantId,
                    Optional.ofNullable(date).orElse(LocalDate.now()));
        }
    }
}
