package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.repository.RestaurantRepository;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.repository.VoteEntryRepository;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteEntryService {
    private final VoteEntryRepository voteEntryRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public List<VoteEntryTo> getAll(long userId) {
        return null;
    }

    public List<VoteEntryTo> getAll(long userId, LocalDate dateStart, LocalDate dateEnd) {
        return null;
    }

    @Transactional
    public VoteEntryTo create(long userId, long restaurantId) {
        return null;
    }

    @Transactional
    public void deleteVote(long userId, long restaurantId) {
    }
}
