package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.model.VoteEntry;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static sergesv.rvs.util.DateTimeUtil.*;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteEntryService {
    private final VoteEntryRepository voteEntryRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private final RvsPropertyResolver propertyResolver;

    public PageTo<VoteEntryTo> getAll(long userId, Pageable pageable) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserId(userId, pageable));
    }

    public PageTo<VoteEntryTo> getAll(long userId, LocalDate dateStart, LocalDate dateEnd,
                                    Pageable pageable) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserIdAndDateBetween(userId, dateStart,
                dateEnd, pageable));
    }

    @Transactional
    public VoteEntryTo create(long userId, long restaurantId) {
        LocalDate currentDate = getCurrentDate();
        LocalTime currentTime = getCurrentTime();

        var voteEntryOptional = voteEntryRepository.findByUserIdAndDate(userId, currentDate);

        if (voteEntryOptional.isPresent()) {
            checkException(checkTime(currentTime, propertyResolver.getMaxVoteTime()),
                    voteAgainSupplier(propertyResolver.getMaxVoteTime()));
            VoteEntry voteEntry = voteEntryOptional.get();
            voteEntry.setDate(currentDate);
            voteEntry.setTime(currentTime);
            voteEntry.setRestaurant(restaurantRepository.getOne(restaurantId));

            return toTo(voteEntryRepository.save(voteEntry));
        }

        return toTo(voteEntryRepository.save(new VoteEntry(0,
                userRepository.getOne(userId), restaurantRepository.getOne(restaurantId),
                currentDate, currentTime)));
    }

    @Transactional
    public void delete(long userId, long restaurantId) {
        checkException(checkTime(getCurrentTime(), propertyResolver.getMaxVoteTime()),
                voteAgainSupplier(propertyResolver.getMaxVoteTime()));
        voteEntryRepository.deleteByUserIdAndRestaurantIdAndDate(userId, restaurantId,
                getCurrentDate());
    }

    private static PageTo<VoteEntryTo> toVoteEntryTos(Page<VoteEntry> voteEntryPage) {
        return toTo(voteEntryPage, page -> page.get()
                .map(ToUtil::toTo)
                .collect(Collectors.toList()));
    }
}
