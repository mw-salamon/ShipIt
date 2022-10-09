package pl.lets_eat_together.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lets_eat_together.email.*;
import pl.lets_eat_together.model.*;
import pl.lets_eat_together.service.*;
import pl.lets_eat_together.user.User;
import pl.lets_eat_together.service.OfficeService;
import pl.lets_eat_together.service.OrderService;
import pl.lets_eat_together.service.PaymentService;
import pl.lets_eat_together.service.UserModelService;

import javax.transaction.Transactional;


@AllArgsConstructor
public class EditStatusForm extends FormLayout {
    TextField restaurant = new TextField("Restaurant");
    TextField meal = new TextField("Meal");
    TextArea notes = new TextArea("Notes");
    DateTimePicker callDeadline = new DateTimePicker("Call deadline");
    TextField pickUpPlace = new TextField("Pick up place");
    ComboBox<Office> office = new ComboBox<>("Office");
    MultiSelectListBox<Payment> payment = new MultiSelectListBox<>();
    ComboBox<Status> status = new ComboBox<>("Status");
    NumberField maxComments = new NumberField("Max comments");

    Button save = new Button("Save");
    OrderService orderService;
    OfficeService officeService;
    UserModelService userModelService;
    Notification notification;
    StatusEmailService statusEmailService;
    EmailTemplate emailTemplate;
    MailNotificationService mailNotificationService;

    public EditStatusForm(OrderService orderService, PaymentService paymentService, OfficeService officeService,
                          UserModelService userModelService, Order order, StatusEmailService statusEmailService,
                          MailNotificationService mailNotificationService){
        this.orderService = orderService;
        this.officeService = officeService;
        this.userModelService = userModelService;
        this.office.setItems(officeService.getAllOffices());
        this.status.setItems(Status.values());
        this.statusEmailService = statusEmailService;
        this.mailNotificationService = mailNotificationService;

        restaurant.setValue(order.getRestaurant());
        meal.setValue(order.getMeal());
        notes.setValue(order.getNote());
        callDeadline.setValue(order.getCallDeadline());
        pickUpPlace.setValue(order.getPickUpPlace());
        office.setValue(order.getOffice());
        status.setValue(order.getStatus());
        maxComments.setValue((double) order.getMaxComments());

        save.addClickListener(event -> updateOrder(order));

        add(restaurant, meal, notes, callDeadline, pickUpPlace, office, status, maxComments, save);

    }


    @Transactional
    private void updateOrder(Order order) {
        boolean hasChanged;

        if(order.getStatus().equals(status.getValue())){
            hasChanged=false;
        }else{
            hasChanged=true;
            emailTemplate = new EmailTemplate(mailNotificationService);
        }

        order.setMeal(meal.getValue());
        order.setNote(notes.getValue());
        order.setRestaurant(restaurant.getValue());
        order.setCallDeadline(callDeadline.getValue());
        order.setPickUpPlace(pickUpPlace.getValue());
        order.setOffice(office.getValue());
        order.setStatus(status.getValue());
        order.setMaxComments(maxComments.getValue().intValue());

        if(hasChanged){
            for (Comment com:
                 order.getComments()) {
                statusEmailService.send(com.getUser().getEmail(),  emailTemplate.statusEmail(status.getValue(), order));
            }
        }

        try{
            orderService.updateOrderStatus(order);
            notification = Notification.show("Udało się edytować ogłoszenie");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().getPage().reload();
        }catch(Exception e){
            notification = Notification.show("Nie udało się edytować ogłoszenia");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }
    }

}
