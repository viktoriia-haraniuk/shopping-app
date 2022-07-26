package shopping.app.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shopping.app.model.User;
import shopping.app.service.UserService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Can't find user by username " + username));
        UserBuilder builder = withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRole().getRoleName().toString());
        return builder.build();
    }
}
