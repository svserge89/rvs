package sergesv.rvs.util.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sergesv.rvs.model.security.UserDetailsImpl;

public final class SecurityUtil {
    public static long getAuthUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ((UserDetailsImpl)authentication.getPrincipal()).getId();
    }

    private SecurityUtil() {
    }
}
