package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.VoteEntry;

@Repository
public interface VoteEntryRepository extends JpaRepository<VoteEntry, Long> {
}
