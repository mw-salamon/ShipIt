package pl.lets_eat_together.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.registration.token.ConfirmationToken;
import pl.lets_eat_together.registration.token.ConfirmationTokenService;
import pl.lets_eat_together.security.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE= "Not found a user with email %s";
    private ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    @Transactional
    public String register(User user){
        Boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if(userExists == true) {
            // TODO : create more proper exception
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableUser(String email){
        return userRepository.enableUser(email);
    }
}
