package sergesv.rvs.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.service.UserService;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.UserTo;

import java.util.Optional;

import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.web.ControllerUtil.getPageable;
import static sergesv.rvs.util.web.SecurityUtil.getAuthUserId;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final RvsPropertyResolver propertyResolver;

    @GetMapping
    public PageTo<UserTo> getAll(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size,
                                 @RequestParam(required = false) String sort) {
        var sortOptional = Optional.ofNullable(sort);
        Pageable pageable = getPageable(page, size,
                getSort(sortOptional.orElse(propertyResolver.getSortUser()),
                        ID, NICKNAME, EMAIL, FIRST_NAME, LAST_NAME, DESC)
                        .orElse(Sort.by(NICKNAME)), propertyResolver.getUserPageSize());

        return userService.getAll(pageable);
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
