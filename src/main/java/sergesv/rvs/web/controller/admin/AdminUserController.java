package sergesv.rvs.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.service.UserService;
import sergesv.rvs.web.to.UserTo;

import java.util.List;

import static sergesv.rvs.util.WebSecurityUtil.getAuthUserId;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserTo> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserTo getOne(@PathVariable long id) {
        return userService.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTo create(@RequestBody UserTo userTo) {
        return userService.create(userTo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody UserTo userTo) {
        userService.update(id, userTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id, getAuthUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        userService.deleteAll(getAuthUserId());
    }
}
