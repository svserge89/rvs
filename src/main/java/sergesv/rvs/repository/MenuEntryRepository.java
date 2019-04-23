package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.MenuEntry;

@Repository
public interface MenuEntryRepository extends JpaRepository<MenuEntry, Long> {
}
