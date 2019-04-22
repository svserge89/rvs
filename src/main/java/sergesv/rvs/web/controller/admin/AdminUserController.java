package sergesv.rvs.web.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.web.to.UserTo;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    @GetMapping
    public List<UserTo> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public UserTo getOne(@PathVariable long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTo create(@RequestBody UserTo userTo) {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody UserTo userTo) {

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
