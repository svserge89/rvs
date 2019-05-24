package sergesv.rvs.model.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

public class UserDetailsAdapter extends User {
    @Getter
    private long id;

    public UserDetailsAdapter(sergesv.rvs.model.User user) {
        super(user.getNickName(), user.getEncryptedPassword(), !user.getRoles().isEmpty(),
                true, true, true,
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toSet()));
        id = user.getId();
    }
}
