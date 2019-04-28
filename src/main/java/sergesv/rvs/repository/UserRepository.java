package sergesv.rvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmail(String email);

    void deleteAllByIdNot(Long id);
}
