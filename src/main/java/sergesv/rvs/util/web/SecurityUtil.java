package sergesv.rvs.util.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sergesv.rvs.model.security.UserDetailsAdapter;

public final class SecurityUtil {
    public static long getAuthUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ((UserDetailsAdapter)authentication.getPrincipal()).getId();
    }

    private SecurityUtil() {
    }
}
