package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.web.to.UserTo;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserTo> getAll() {
        return null;
    }

    public UserTo getOne(long id) {
        return null;
    }

    public UserTo getByEmail(String email) {
        return null;
    }

    @Transactional
    public UserTo create(UserTo userTo) {
        return null;
    }

    @Transactional
    public void update(long id, UserTo userTo) {
    }

    @Transactional
    public void delete(long id) {
    }

    @Transactional
    public void deleteAll() {
    }
}
