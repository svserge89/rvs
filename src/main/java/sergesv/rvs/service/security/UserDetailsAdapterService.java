package sergesv.rvs.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sergesv.rvs.model.security.UserDetailsAdapter;
import sergesv.rvs.service.UserService;

@Service
@RequiredArgsConstructor
public class UserDetailsAdapterService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsAdapter(userService.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
