package pl.lets_eat_together.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.lets_eat_together.model.Order;

public class SingleOrderView extends VerticalLayout{

    SingleOrderView(Order order){
        this.setAlignItems(Alignment.CENTER);
        add(new Html(("<span><b>Restaurant:</b> " + order.getRestaurant() +"</span>")));
        add(new Html("<span><b>Meal:</b> " + order.getMeal() +"</span>"));
        add(new Html("<span><b>Notes:</b> " + order.getNote() +"</span>"));
        add(new Html("<span><b>Call deadline:</b> " + order.getCallDeadline().toString() +"</span>"));
        add(new Html("<span><b>Status:</b> " + order.getStatus().toString() +"</span>"));
        add(new Html("<span><b>Pick up place:</b> " + order.getPickUpPlace() +"</span>"));

        setSpacing(false);
        setPadding(true);
        getStyle().set("border", "1px solid var(--lumo-primary-color)" );
        getStyle().set("border-radius", "var(--lumo-border-radius-l)");
        getStyle().set("width", "60%");
    }
}
