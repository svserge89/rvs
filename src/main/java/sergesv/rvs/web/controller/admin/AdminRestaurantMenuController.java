package sergesv.rvs.web.controller.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.web.to.MenuEntryTo;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequestMapping("/api/admin/restaurants/{restaurantId}/menu")
public class AdminRestaurantMenuController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuEntryTo create(@PathVariable long restaurantId,
                              @RequestBody MenuEntryTo menuEntryTo) {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @PathVariable long restaurantId,
                       @RequestBody MenuEntryTo menuEntryTo) {

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @PathVariable long restaurantId) {

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(
            @PathVariable long restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {

    }
}
