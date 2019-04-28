package sergesv.rvs.model.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

public class UserDetailsImpl extends User {
    @Getter
    private long id;

    public UserDetailsImpl(long id, String userName, String password, Set<GrantedAuthority> roles) {
        super(userName, password, roles);

        this.id = id;
    }
}
