package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.MailNotification;
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

    public List<MailNotification> getAllMailNotifications(){
        return mailNotificationRepository.findAll();
    }

    public MailNotification getMailNotificationById(Long id){
        Optional<MailNotification> found = mailNotificationRepository.findById(id);
        return found.orElseThrow();
    }

    public MailNotification getMailNotificationByName(String name){
        Optional<MailNotification> found = mailNotificationRepository.findByName(name);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public MailNotification addMailNotification(MailNotification newMailNotification){
        return mailNotificationRepository.saveAndFlush(newMailNotification);
    }


    public String deleteMailNotification(Long id){
         MailNotification mailNotification = getMailNotificationById(id);
         mailNotificationRepository.delete(mailNotification);
         return "Notification with id=" + id + " deleted";
    }

}
