package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RvsPropertyResolver propertyResolver;

    public Page<VoteEntryTo> getAll(long userId, Pageable pageable) {
        log.debug("getAll params: pageable=\"{}\", userId={}", pageable, userId);

        return voteEntryRepository.findAllByUserId(userId, pageable).map(ToUtil::toTo);
    }

    public Page<VoteEntryTo> getAll(long userId, LocalDate dateStart, LocalDate dateEnd,
                                    Pageable pageable) {
        log.debug("getAll params: pageable=\"{}\", userId={}, dateStart={}, dateEnd={}", pageable,
                userId, dateStart, dateEnd);

        return voteEntryRepository.findAllByUserIdAndDateBetween(userId, dateStart, dateEnd,
                pageable).map(ToUtil::toTo);
    }

    @Transactional
    public VoteEntryTo create(long userId, long restaurantId) {
        log.debug("create params: userId={}, restaurantId={}", userId, restaurantId);

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
        log.debug("delete params: userId={}, restaurantId={}", userId, restaurantId);

        LocalDate currentDate = getCurrentDate();

        checkException(checkTime(getCurrentTime(), propertyResolver.getMaxVoteTime()),
                voteAgainSupplier(propertyResolver.getMaxVoteTime()));
        checkException(voteEntryRepository.deleteByUserIdAndRestaurantIdAndDate(userId,
                restaurantId, currentDate) != 0, voteEntryNotFoundSupplier(userId, restaurantId,
                currentDate));
    }
}
