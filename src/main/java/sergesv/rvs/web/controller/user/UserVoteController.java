package sergesv.rvs.web.controller.user;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequestMapping("/api/user")
public class UserVoteController {
    @GetMapping("/votes")
    public List<VoteEntryTo> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
        return null;
    }

    @PostMapping("/restaurants/{restaurantId}/vote")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteEntryTo create(@PathVariable long restaurantId) {
        return null;
    }

    @DeleteMapping("/restaurants/{restaurantId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long restaurantId) {

    }
}
