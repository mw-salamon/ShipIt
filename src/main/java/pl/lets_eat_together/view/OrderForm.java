package pl.lets_eat_together.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.OrderService;

import javax.transaction.Transactional;


public class OrderForm extends FormLayout {
    TextField restaurant = new TextField("Restaurant");
    TextField meal = new TextField("Meal");
    TextArea notes = new TextArea("Notes");
    DateTimePicker callDeadline = new DateTimePicker("Call deadline");
    TextField pickUpPlace = new TextField("Pick up place");
    NumberField maxComments = new NumberField("Max comments");

    Button save = new Button("Save");
    OrderService orderService;
    Notification notification;


    public OrderForm(OrderService orderService){
        this.orderService = orderService;
        save.addClickListener(event -> saveOrder());
        add(restaurant, meal, notes, callDeadline, pickUpPlace, maxComments, save);

    }

    @Transactional
    private void saveOrder() {

        Order newOrder = new Order();
        newOrder.setMeal(meal.getValue());
        newOrder.setNote(notes.getValue());
        newOrder.setRestaurant(restaurant.getValue());
        newOrder.setCallDeadline(callDeadline.getValue());
        newOrder.setPickUpPlace(pickUpPlace.getValue());
        newOrder.setMaxComments(maxComments.getValue().intValue());

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
