package pl.lets_eat_together.email;

public interface StatusEmailSender {
    void send(String sendTo, String email);
}
