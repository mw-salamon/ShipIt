package pl.lets_eat_together.registration;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.email.EmailSender;
import pl.lets_eat_together.email.EmailTemplate;
import pl.lets_eat_together.registration.token.ConfirmationToken;
import pl.lets_eat_together.registration.token.ConfirmationTokenService;
import pl.lets_eat_together.user.User;
import pl.lets_eat_together.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private EmailSender emailSender;

    public String register(RegistrationRequest request) {

        Boolean isValidEmail = emailValidator.checkIfEmailIsCorrect(request.getEmail());

        logger.info("Email is valid: " + isValidEmail);

        if(!isValidEmail){
            //TODO : create more proper exception; think about exception-handler to have still running app
            throw new IllegalStateException("Email is not valid");
        }
        String token = userService.register(
                new User(
                        request.getFirstName(),
                        request.getSureName(),
                        request.getEmail(),
                        request.getPassword()));
        emailSender.send(request.getEmail(), EmailTemplate.buildEmail(request.getFirstName(), token));
        return token;
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedTime() != null){
            throw new IllegalStateException("email is already confirmed");
        }

        //todo think about change the name
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedTime(token);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
