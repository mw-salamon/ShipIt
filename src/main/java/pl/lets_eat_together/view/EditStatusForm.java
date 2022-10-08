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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lets_eat_together.model.*;
import pl.lets_eat_together.service.OfficeService;
import pl.lets_eat_together.service.OrderService;
import pl.lets_eat_together.service.PaymentService;
import pl.lets_eat_together.service.UserModelService;
import pl.lets_eat_together.user.User;

import javax.transaction.Transactional;


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


    public EditStatusForm(OrderService orderService, PaymentService paymentService, OfficeService officeService,
                          UserModelService userModelService, Order order){
        this.orderService = orderService;
        this.officeService = officeService;
        this.userModelService = userModelService;
        this.office.setItems(officeService.getAllOffices());
        this.payment.setItems(paymentService.getAllPayments());
        this.status.setItems(Status.values());

        restaurant.setValue(order.getRestaurant());
        meal.setValue(order.getMeal());
        notes.setValue(order.getNote());
        callDeadline.setValue(order.getCallDeadline());
        pickUpPlace.setValue(order.getPickUpPlace());
        office.setValue(order.getOffice());
        payment.select(order.getPayments());
        status.setValue(order.getStatus());
        maxComments.setValue((double) order.getMaxComments());

        save.addClickListener(event -> updateOrder(order));

        add(restaurant, meal, notes, callDeadline, pickUpPlace, office, payment, status, maxComments, save);

    }


    @Transactional
    private void updateOrder(Order order) {
        order.setMeal(meal.getValue());
        order.setNote(notes.getValue());
        order.setRestaurant(restaurant.getValue());
        order.setCallDeadline(callDeadline.getValue());
        order.setPickUpPlace(pickUpPlace.getValue());
        order.setOffice(office.getValue());
        order.setPayments(payment.getValue());
        order.setStatus(status.getValue());
        System.out.println(status.getValue());
        order.setMaxComments(maxComments.getValue().intValue());

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
