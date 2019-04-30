package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.User;
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

    public List<VoteEntryTo> getAll(long userId) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserId(userId));
    }

    public List<VoteEntryTo> getAll(long userId, LocalDate dateStart, LocalDate dateEnd) {
        return toVoteEntryTos(voteEntryRepository.findAllByUserIdAndDateBetween(userId, dateStart,
                dateEnd));
    }

    @Transactional
    public VoteEntryTo create(long userId, long restaurantId) {
        checkExists(userRepository.existsById(userId), userNotFoundSupplier(userId));
        checkExists(restaurantRepository.existsById(restaurantId),
                restaurantNotFoundSupplier(restaurantId));

        LocalDate currentDate = getCurrentDate();
        LocalTime currentTime = getCurrentTime();

        if (voteEntryRepository.existsByUserIdAndDate(userId, currentDate)) {
            if (checkTime(currentTime)) {
                voteEntryRepository.deleteByUserIdAndDate(userId, currentDate);

                return toTo(saveVote(userId, restaurantId, currentDate, currentTime));
            }
        } else {
            return toTo(saveVote(userId, restaurantId, currentDate, currentTime));
        }

        return null;
    }

    @Transactional
    public void deleteVote(long userId, long restaurantId) {
        if (checkTime(getCurrentTime())) {
            voteEntryRepository.deleteByUserIdAndRestaurantIdAndDate(userId, restaurantId,
                    getCurrentDate());
        }
    }

    private VoteEntry saveVote(long userId, long restaurantId, LocalDate date, LocalTime time) {
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        VoteEntry voteEntry = new VoteEntry(0, user, restaurant, date, time);

        return voteEntryRepository.save(voteEntry);
    }

    private static List<VoteEntryTo> toVoteEntryTos(List<VoteEntry> voteEntries) {
        return voteEntries.stream()
                .map(ToUtil::toTo)
                .collect(Collectors.toList());
    }
}
