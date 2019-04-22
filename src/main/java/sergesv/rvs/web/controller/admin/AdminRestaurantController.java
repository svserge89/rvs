package sergesv.rvs.web.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.web.to.RestaurantTo;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantTo create(@RequestBody RestaurantTo restaurantTo) {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody RestaurantTo restaurantTo) {

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {

    }
}
