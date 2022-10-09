package pl.lets_eat_together.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.lets_eat_together.email.StatusEmailService;
import pl.lets_eat_together.model.Comment;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.model.Status;
import pl.lets_eat_together.service.*;
import pl.lets_eat_together.user.User;

import java.util.List;
import java.util.Objects;

public class SingleOrderView extends VerticalLayout{

    Dialog addCommentDialog = new Dialog();
    Dialog editOrderDialog = new Dialog();
    Order order;

    CommentService commentService;
    UserModelService userModelService;
    PaymentService paymentService;
    OfficeService officeService;
    OrderService orderService;
    StatusEmailService statusEmailService;
    MailNotificationService mailNotificationService;
    List<Comment> comments;
    VerticalLayout commentViews = new VerticalLayout();

    SingleOrderView(CommentService commentService, UserModelService userModelService, OrderService orderService,
                    PaymentService paymentService, OfficeService officeService, Order order,
                    StatusEmailService statusEmailService, MailNotificationService mailNotificationService){
        this.order = order;
        this.commentService = commentService;
        this.userModelService = userModelService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.officeService = officeService;
        this.statusEmailService = statusEmailService;
        this.mailNotificationService = mailNotificationService;

        updateList();
        configureList();
        createAddCommentDialog();
        createEditOrderDialog();


        this.setAlignItems(Alignment.CENTER);
        add(new Html(("<span style=\"font-size:larger\"><b>Restaurant:</b> " + order.getRestaurant() +"</span>")));
        add(new Html("<span><b>Meal:</b> " + order.getMeal() +"</span>"));
        add(new Html("<span><b>Notes:</b> " + order.getNote() +"</span>"));
        add(new Html("<span><b>Call deadline:</b> " + order.getCallDeadline().toString() +"</span>"));
        add(new Html("<span><b>Status:</b> " + order.getStatus().toString() +"</span>"));
        add(new Html("<span><b>Office:</b> " + order.getOffice().toString() +"</span>"));
        add(new Html("<span><b>Pick up place:</b> " + order.getPickUpPlace() +"</span>"));
        add(new Html("<span><b>Payment methods:</b> " + order.getAllPaymentMethods() +"</span>"));


        Span email = new Span(order.getUser().getEmail());
        email.getStyle().set("width", "100%");

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HorizontalLayout bottomBar = new HorizontalLayout(email);
        bottomBar.getStyle().set("width", "100%");
        if(Objects.equals(userDetails.getUsername(), order.getUser().getEmail())
                && !order.getStatus().equals(Status.CLOSED)
                && !order.getStatus().equals(Status.CANCELED)){
            Button editOrder = new Button("Edit order");
            editOrder.getStyle().set("width", "100%");
            editOrder.addClickListener(event -> editOrderDialog.open());
            bottomBar.add(editOrder);
        }else if (!order.getStatus().equals(Status.CLOSED)
                && !order.getStatus().equals(Status.CANCELED)){
            Button addComment = new Button("Add your comment", e -> addCommentDialog.open());
            addComment.getStyle().set("width", "100%");
            addComment.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            bottomBar.add(addComment);
        }

        add(bottomBar);

        Accordion accordion = new Accordion();
        accordion.getStyle().set("width", "100%");
        accordion.close();
        accordion.add("Comments/suborders", commentViews);

        add(accordion);
        setSpacing(false);
        setPadding(true);
        getStyle().set("border", "1px solid var(--lumo-primary-color)" );
        getStyle().set("border-radius", "var(--lumo-border-radius-l)");
        getStyle().set("width", "60%");

        if(order.getStatus().equals(Status.CLOSED)){
            getStyle().set("background-color", "var(--lumo-contrast-30pct)" );
        }else if(order.getStatus().equals(Status.CANCELED)){
            getStyle().set("background-color", "var(--lumo-error-color-50pct)" );
        }
    }

    private void updateList() {
        this.comments = this.order.getComments();
    }

    private void createAddCommentDialog(){
        addCommentDialog.setHeaderTitle("Add a comment, your suborder");

        CommentForm commentForm = new CommentForm(this.commentService, this.userModelService, this.order);
        addCommentDialog.add(commentForm);

        Button cancelButton = new Button("Cancel", e -> addCommentDialog.close());
        addCommentDialog.getFooter().add(cancelButton);

        add(addCommentDialog);
    }

    private void configureList() {
        commentViews = new VerticalLayout();
        commentViews.setAlignItems(Alignment.CENTER);
        for (Comment comment: comments){
            commentViews.add(new SingleCommentView(commentService, userModelService, order, comment));
        }
    }

    private void createEditOrderDialog(){
        editOrderDialog.setHeaderTitle("Edit your order");
        editOrderDialog.setWidth("40em");

        EditStatusForm editOrderForm = new EditStatusForm(this.orderService, this.paymentService, this.officeService,
                                            this.userModelService, this.order, this.statusEmailService, this.mailNotificationService);
        editOrderDialog.add(editOrderForm);

        Button cancelButton = new Button("Cancel", e -> editOrderDialog.close());
        editOrderDialog.getFooter().add(cancelButton);

        add(editOrderDialog);
    }
}
