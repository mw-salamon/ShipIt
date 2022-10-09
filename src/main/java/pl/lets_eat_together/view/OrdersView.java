package pl.lets_eat_together.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.lets_eat_together.email.StatusEmailService;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.*;
import pl.lets_eat_together.user.UserService;

import java.util.List;

@Route("orders")
@PageTitle("Orders | Let's eat together")
public class OrdersView extends VerticalLayout {
    Dialog dialog = new Dialog();
    TextField filterText = new TextField();
    OrderService orderService;
    PaymentService paymentService;
    OfficeService officeService;
    UserModelService userModelService;
    CommentService commentService;
    MailNotificationService mailNotificationService;
    StatusEmailService statusEmailService;

    List<Order> openedOrders;
    VerticalLayout openedOrderViews = new VerticalLayout();

    List<Order> closedOrders;
    VerticalLayout closedOrderViews = new VerticalLayout();

    public OrdersView(OrderService orderService, PaymentService paymentService, OfficeService officeService,
                      UserModelService userModelService, CommentService commentService,
                      StatusEmailService statusEmailService, MailNotificationService mailNotificationService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.officeService = officeService;
        this.userModelService = userModelService;
        this.commentService = commentService;
        this.statusEmailService = statusEmailService;
        this.mailNotificationService = mailNotificationService;
        createDialog();
        updateList();
        updateClosedList();

        addClassName("list-view");
        setSizeFull();
        configureList();
        configureClosedList();

        Accordion accordion = new Accordion();
        accordion.getStyle().set("width", "100%");
        accordion.close();
        accordion.add("Closed / cancelled orders", closedOrderViews);

        add(getToolbar(), getContent(), accordion);
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(openedOrderViews);
        content.setWidth("100%");
        return content;
    }

    private void configureList() {
        openedOrderViews = new VerticalLayout();
        openedOrderViews.setAlignItems(Alignment.CENTER);
        for (Order order : openedOrders) {
            openedOrderViews.add(new SingleOrderView(this.commentService, this.userModelService, this.orderService, this.paymentService, this.officeService, order,
                                                     this.statusEmailService, this.mailNotificationService));
        }
    }

    private void configureClosedList() {
        closedOrderViews = new VerticalLayout();
        closedOrderViews.setAlignItems(Alignment.CENTER);
        for (Order order: closedOrders){
            closedOrderViews.add(new SingleOrderView(this.commentService, this.userModelService, this.orderService,
                                                     this.paymentService, this.officeService, order, this.statusEmailService, this.mailNotificationService));
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addOrderButton = new Button("Add order");
        addOrderButton.addClickListener(e -> dialog.open());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        openedOrders = orderService.findAllOpenedOrders();
    }

    private void updateClosedList() {
        closedOrders = orderService.findAllClosedOrCancelled();
    }

    public void reload(){
        updateList();
        configureList();
    }

    private void createDialog(){
        dialog.setHeaderTitle("Add a new order");
        dialog.setWidth("40em");

        OrderForm orderForm = new OrderForm(this.orderService, this.paymentService, this.officeService,
                                            this.userModelService);
        dialog.add(orderForm);

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);

        add(dialog);
    }

}