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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lets_eat_together.model.Office;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.model.Payment;
import pl.lets_eat_together.model.UserModel;
import pl.lets_eat_together.service.OfficeService;
import pl.lets_eat_together.service.OrderService;
import pl.lets_eat_together.service.PaymentService;
import pl.lets_eat_together.service.UserModelService;
import pl.lets_eat_together.user.User;
import pl.lets_eat_together.user.UserService;

import javax.transaction.Transactional;
import java.util.Set;


public class OrderForm extends FormLayout {
    TextField restaurant = new TextField("Restaurant");
    TextField meal = new TextField("Meal");
    TextArea notes = new TextArea("Notes");
    DateTimePicker callDeadline = new DateTimePicker("Call deadline");
    TextField pickUpPlace = new TextField("Pick up place");
    ComboBox<Office> office = new ComboBox<>("Office");
    MultiSelectListBox<Payment> payment = new MultiSelectListBox<>();
    NumberField maxComments = new NumberField("Max comments");

    Button save = new Button("Save");
    OrderService orderService;
    OfficeService officeService;
    UserModelService userModelService;
    Notification notification;


    public OrderForm(OrderService orderService, PaymentService paymentService, OfficeService officeService,
                     UserModelService userModelService){
        this.orderService = orderService;
        this.officeService = officeService;
        this.userModelService = userModelService;
        this.office.setItems(officeService.getAllOffices());
        this.payment.setItems(paymentService.getAllPayments());
        save.addClickListener(event -> saveOrder());
        add(restaurant, meal, notes, callDeadline, pickUpPlace, office, payment, maxComments, save);

    }

    @Transactional
    private void saveOrder() {
        Order newOrder = new Order();
        newOrder.setMeal(meal.getValue());
        newOrder.setNote(notes.getValue());
        newOrder.setRestaurant(restaurant.getValue());
        newOrder.setCallDeadline(callDeadline.getValue());
        newOrder.setPickUpPlace(pickUpPlace.getValue());
        newOrder.setOffice(office.getValue());
        newOrder.setPayments(payment.getValue());
        newOrder.setMaxComments(maxComments.getValue().intValue());

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            UserModel userModel = userModelService.loadUserModelByUsername(userDetails.getUsername());
            newOrder.setUser(userModel);
        }catch(UsernameNotFoundException e){
            UserModel userModel = new UserModel();
            userModel.setFirstName(userDetails.getFirstName());
            userModel.setEmail(userDetails.getEmail());
            userModel.setSureName(userDetails.getSureName());
            userModel.setPassword(userDetails.getPassword());
            userModelService.addUserModel(userModel);
            newOrder.setUser(userModel);
        }


        try{
            orderService.addNewOrder(newOrder);
            notification = Notification.show("Udało się dodać ogłoszenie");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().getPage().reload();
        }catch(Exception e){
            notification = Notification.show("Nie udało się dodać ogłoszenia");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }
    }
}
