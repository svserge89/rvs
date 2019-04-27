package sergesv.rvs.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.service.VoteEntryService;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.WebControllerUtil.ParamsCondition.BETWEEN_DATES;
import static sergesv.rvs.util.WebControllerUtil.resolveParams;
import static sergesv.rvs.util.WebSecurityUtil.getAuthUserId;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserVoteController {
    private final VoteEntryService voteEntryService;

    @GetMapping("/votes")
    public List<VoteEntryTo> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd) {
        if (resolveParams(dateStart, dateEnd) == BETWEEN_DATES) {
            return voteEntryService.getAll(getAuthUserId(),
                    Optional.ofNullable(dateStart).orElse(LocalDate.MIN),
                    Optional.ofNullable(dateEnd).orElse(LocalDate.MAX));
        } else {
            return voteEntryService.getAll(getAuthUserId());
        }
    }

    @PostMapping("/restaurants/{restaurantId}/vote")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteEntryTo create(@PathVariable long restaurantId) {
        return voteEntryService.create(getAuthUserId(), restaurantId);
    }

    @DeleteMapping("/restaurants/{restaurantId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long restaurantId) {
        voteEntryService.deleteVote(getAuthUserId(), restaurantId);
    }
}
