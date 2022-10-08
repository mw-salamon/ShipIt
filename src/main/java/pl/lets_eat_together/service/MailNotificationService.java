package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.Notification;
import pl.lets_eat_together.repository.MailNotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MailNotificationService {

    private final MailNotificationRepository mailNotificationRepository;

    @Autowired
    public MailNotificationService(@Qualifier("mailNotificationRepository") MailNotificationRepository mailNotificationRepository) {
        this.mailNotificationRepository = mailNotificationRepository;
    }

    public List<Notification> getAllMailNotifications(){
        return mailNotificationRepository.findAll();
    }

    public Notification getMailNotificationById(Long id){
        Optional<Notification> found = mailNotificationRepository.findById(id);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public Notification addMailNotification(Notification newMailNotification){
        return mailNotificationRepository.saveAndFlush(newMailNotification);
    }


    public String deleteMailNotification(Long id){
         Notification mailNotification = getMailNotificationById(id);
         mailNotificationRepository.delete(mailNotification);
         return "Notification with id=" + id + " deleted";
    }

}
