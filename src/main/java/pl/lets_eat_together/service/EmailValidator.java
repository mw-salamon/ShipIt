package pl.lets_eat_together.service;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator {
    public boolean checkIfEmailIsCorrect(String email) {
        if(email.contains("capgemini.com")) {
            return true;
        }
        return false;
    }
}
