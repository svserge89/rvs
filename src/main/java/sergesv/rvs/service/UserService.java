package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.User;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.UserTo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static sergesv.rvs.util.ToUtil.toModel;
import static sergesv.rvs.util.ToUtil.toTo;
import static sergesv.rvs.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserTo> getAll() {
        return userRepository.findAll().stream()
                .map(ToUtil::toTo)
                .collect(Collectors.toList());
    }

    public UserTo getOne(long id) {
        return toTo(userRepository.findById(id).orElseThrow(userNotFoundSupplier(id)));
    }

    public Optional<User> findByUserName(String userName) {
        var user = userRepository.findByNickName(userName);

        return user.isPresent() ? user : userRepository.findByEmail(userName);
    }

    @Transactional
    public UserTo create(UserTo userTo) {
        return toTo(checkException(() -> userRepository.save(toModel(userTo, passwordEncoder))));
    }

    @Transactional
    public void update(long id, UserTo userTo) {
        checkException(userRepository.existsById(id), userNotFoundSupplier(id));
        checkException(() -> userRepository.save(toModel(id, userTo, passwordEncoder)));
    }

    @Transactional
    public void delete(long id, long authUserId) {
        if (id != authUserId) {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteAll(long authUserId) {
        userRepository.deleteAllByIdNot(authUserId);
    }
}
