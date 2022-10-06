package pl.lets_eat_together.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.OrderService;

import java.util.Collections;

@Route("orders")
@PageTitle("Orders | Let's eat together")
public class OrdersView extends VerticalLayout {
    Grid<Order> grid = new Grid<>(Order.class);
    TextField filterText = new TextField();
//        OrderForm form;
    OrderService service;

    public OrdersView(OrderService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        System.out.println(
                "HELOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    }

    private Component getContent() {
//            HorizontalLayout content = new HorizontalLayout(grid, form);
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
//            content.setFlexGrow(1, form);
        content.setFlexGrow(1);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
//            form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
//            form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("restaurant", "meal", "status", "callDeadline", "pickUpPlace", "maxComments");
        grid.addColumn(order -> order.getRestaurant()).setHeader("Restaurant");
        grid.addColumn(order -> order.getMeal()).setHeader("Meal");
        grid.addColumn(order -> order.getStatus()).setHeader("Status");
        grid.addColumn(order -> order.getCallDeadline()).setHeader("callDeadline");
        grid.addColumn(order -> order.getPickUpPlace()).setHeader("pickUpPlace");
        grid.addColumn(order -> order.getMaxComments()).setHeader("maxComments");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addOrderButton = new Button("Add order");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.getAllOrders());
    }
}