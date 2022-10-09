package pl.lets_eat_together.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lets_eat_together.user.User;

@Component
public class SecurityService {

    public User getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) context.getAuthentication().getPrincipal();
        }
        // Anonymous or no authentication.
        return null;
    }
}