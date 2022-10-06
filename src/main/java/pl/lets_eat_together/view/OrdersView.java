package pl.lets_eat_together.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.OrderService;

import java.util.List;

@Route("orders")
@PageTitle("Orders | Let's eat together")
public class OrdersView extends VerticalLayout {
    TextField filterText = new TextField();
    OrderService orderService;

    OrderForm form;

    List<Order> orders;
    VerticalLayout orderViews = new VerticalLayout();

    public OrdersView(OrderService service) {
        this.orderService = service;
        updateList();

        addClassName("list-view");
        setSizeFull();
        configureList();
        configureForm();

        add(getToolbar(), getContent());


    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(orderViews, form);
        content.setFlexGrow(2, orderViews);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new OrderForm( orderService);
        form.setWidth("25em");
        form.setVisible(false);
    }

    private void configureList() {
        orderViews = new VerticalLayout();
        orderViews.setAlignItems(Alignment.CENTER);
        for (Order order: orders){
            orderViews.add(new SingleOrderView(order));
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addOrderButton = new Button("Add order");
        addOrderButton.addClickListener(e -> form.setVisible(!form.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        orders = orderService.getAllOrders();
    }

    public void reload(){
        updateList();
        configureList();
    }

}