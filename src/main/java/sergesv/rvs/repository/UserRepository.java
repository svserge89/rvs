package sergesv.rvs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sergesv.rvs.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @EntityGraph(attributePaths = "roles")
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByNickName(String nickname);

    @Modifying
    @Query("DELETE FROM User user WHERE user.id = :id")
    Integer delete(Long id);

    @Modifying
    @Query("DELETE FROM User user WHERE user.id <> :id")
    void deleteAllByIdNot(Long id);
}
