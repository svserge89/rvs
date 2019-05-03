package sergesv.rvs.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.service.VoteEntryService;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.*;
import static sergesv.rvs.util.DateTimeUtil.MAX_DATE;
import static sergesv.rvs.util.DateTimeUtil.MIN_DATE;
import static sergesv.rvs.util.SortUtil.*;
import static sergesv.rvs.util.web.ControllerUtil.ParamsCondition.BETWEEN_DATES;
import static sergesv.rvs.util.web.ControllerUtil.getPageable;
import static sergesv.rvs.util.web.ControllerUtil.resolveParams;
import static sergesv.rvs.util.web.SecurityUtil.getAuthUserId;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserVoteController {
    private final VoteEntryService voteEntryService;
    private final RvsPropertyResolver propertyResolver;

    @GetMapping("/votes")
    public PageTo<VoteEntryTo> getAll(@RequestParam(required = false)
                                      @DateTimeFormat(iso = ISO.DATE) LocalDate dateStart,
                                      @RequestParam(required = false)
                                      @DateTimeFormat(iso = ISO.DATE) LocalDate dateEnd,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size,
                                      @RequestParam(required = false) String sort) {
        Pageable pageable = getPageable(page, size,
                getSort(sort, DATE, TIME, DESC).orElse(Sort.by(DATE, TIME).descending()),
                propertyResolver.getVoteEntryPageSize());

        if (resolveParams(dateStart, dateEnd) == BETWEEN_DATES) {
            return voteEntryService.getAll(getAuthUserId(),
                    Optional.ofNullable(dateStart).orElse(MIN_DATE),
                    Optional.ofNullable(dateEnd).orElse(MAX_DATE), pageable);
        } else {
            return voteEntryService.getAll(getAuthUserId(), pageable);
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
        voteEntryService.delete(getAuthUserId(), restaurantId);
    }
}
