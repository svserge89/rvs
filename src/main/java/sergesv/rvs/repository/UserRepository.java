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
    @EntityGraph(User.GRAPH_WITH_ROLES)
    Page<User> findAll(Pageable pageable);

    @EntityGraph(User.GRAPH_WITH_ROLES)
    Optional<User> findByEmail(String email);

    @EntityGraph(User.GRAPH_WITH_ROLES)
    Optional<User> findByNickName(String nickname);

    @Modifying
    @Query("DELETE FROM User user WHERE user.id = :id")
    Integer delete(Long id);

    @Modifying
    @Query("DELETE FROM User user WHERE user.id <> :id")
    void deleteAllByIdNot(Long id);
}
