package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.RvsPropertyResolver;
import sergesv.rvs.model.VoteEntry;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<VoteEntryTo> getAll(long userId, Pageable pageable) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserId(userId, pageable).get());
    }

    public List<VoteEntryTo> getAll(long userId, LocalDate dateStart, LocalDate dateEnd,
                                    Pageable pageable) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserIdAndDateBetween(userId, dateStart,
                dateEnd, pageable).get());
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

    private static List<VoteEntryTo> toVoteEntryTos(Stream<VoteEntry> voteEntryStream) {
        return voteEntryStream
                .map(ToUtil::toTo)
                .collect(Collectors.toList());
    }
}
