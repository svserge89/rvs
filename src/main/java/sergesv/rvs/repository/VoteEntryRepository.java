package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.VoteEntry;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VoteEntryRepository extends JpaRepository<VoteEntry, Long> {
    @Query("SELECT COUNT (voteEntry) FROM VoteEntry voteEntry " +
            "WHERE voteEntry.restaurant.id = :restaurantId")
    Integer countByRestaurantId(Long restaurantId);

    @Query("SELECT COUNT (voteEntry) FROM VoteEntry voteEntry " +
            "WHERE voteEntry.restaurant.id = :restaurantId AND voteEntry.date = :date")
    Integer countByRestaurantIdAndDateEquals(Long restaurantId, LocalDate date);

    @Query("SELECT COUNT (voteEntry) FROM VoteEntry voteEntry " +
            "WHERE voteEntry.restaurant.id = :restaurantId " +
                "AND voteEntry.date BETWEEN :dateStart AND :dateEnd")
    Integer countByRestaurantIdAndDateBetween(Long restaurantId, LocalDate dateStart,
                                           LocalDate dateEnd);

    @EntityGraph(VoteEntry.GRAPH_WITH_RESTAURANT)
    Page<VoteEntry> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(VoteEntry.GRAPH_WITH_RESTAURANT)
    Page<VoteEntry> findAllByUserIdAndDateBetween(Long userId, LocalDate dateStart,
                                                  LocalDate dateEnd, Pageable pageable);

    @Query("SELECT voteEntry FROM VoteEntry voteEntry WHERE voteEntry.user.id = :userId " +
                "AND voteEntry.date = :date")
    Optional<VoteEntry> findByUserIdAndDate(Long userId, LocalDate date);

    @Modifying
    @Query("DELETE FROM VoteEntry voteEntry WHERE voteEntry.user.id = :userId " +
                "AND voteEntry.restaurant.id = :restaurantId AND voteEntry.date = :date")
    Integer deleteByUserIdAndRestaurantIdAndDate(Long userId, Long restaurantId, LocalDate date);
}
