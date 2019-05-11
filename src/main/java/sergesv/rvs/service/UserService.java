package sergesv.rvs.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergesv.rvs.model.User;
import sergesv.rvs.model.security.Role;
import sergesv.rvs.repository.UserRepository;
import sergesv.rvs.util.ToUtil;
import sergesv.rvs.web.to.PageTo;
import sergesv.rvs.web.to.UserTo;

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

    private Logger log = LoggerFactory.getLogger(getClass());

    public PageTo<UserTo> getAll(Pageable pageable) {
        log.debug("getAll params: pageable=\"{}\"", pageable);

        return toTo(userRepository.findAll(pageable), page -> page.get()
                .map(ToUtil::toTo)
                .collect(Collectors.toList()));
    }

    public UserTo getOne(long id) {
        log.debug("getOne params: id={}", id);

        return toTo(userRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(User.class, id)));
    }

    public Optional<User> findByUserName(String userName) {
        log.debug("findByUserName params: userName={}", userName);

        var user = userRepository.findByNickName(userName);

        return user.isPresent() ? user : userRepository.findByEmail(userName);
    }

    @Transactional
    public UserTo create(UserTo userTo) {
        log.debug("create params: userTo={}", userTo);

        checkPassword(userTo);

        return toTo(checkExistsException(
                () -> userRepository.save(toModel(userTo, passwordEncoder)), User.class));
    }

    @Transactional
    public void update(long id, UserTo userTo) {
        log.debug("update params: id={}, userTo={}", id, userTo);

        User user = userRepository.findById(id)
                .orElseThrow(entityNotFoundSupplier(User.class, id));

        Optional.ofNullable(userTo.getNickName()).ifPresent(user::setNickName);
        Optional.ofNullable(userTo.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userTo.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userTo.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userTo.getPassword())
                .ifPresent(password -> user.setEncryptedPassword(
                        passwordEncoder.encode(checkPassword(userTo))));
        Optional.ofNullable(userTo.getAdmin())
                .ifPresent(enable -> updateUserRole(user, Role.ROLE_ADMIN, enable));
        Optional.ofNullable(userTo.getRegular())
                .ifPresent(enable -> updateUserRole(user, Role.ROLE_USER, enable));

        userRepository.save(user);
    }

    @Transactional
    public void delete(long id, long authUserId) {
        log.debug("delete params: id={}, authUserId={}", id, authUserId);

        if (id != authUserId) {
            checkException(userRepository.delete(id) != 0, entityNotFoundSupplier(User.class, id));
        }
    }

    @Transactional
    public void deleteAll(long authUserId) {
        log.debug("deleteAll params: authUserId={}", authUserId);

        userRepository.deleteAllByIdNot(authUserId);
    }

    private void updateUserRole(User user, Role role, boolean enable) {
        if (enable) {
            user.getRoles().add(role);
        } else {
            user.getRoles().remove(role);
        }
    }
}
