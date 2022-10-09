package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.MailNotification;

import java.util.Optional;


@Repository("mailNotificationRepository")
public interface MailNotificationRepository extends JpaRepository<MailNotification, Long> {

    Optional<MailNotification> findByName(String name);
}