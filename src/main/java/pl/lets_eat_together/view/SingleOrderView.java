package pl.lets_eat_together.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.lets_eat_together.model.Comment;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.CommentService;
import pl.lets_eat_together.service.UserModelService;
import pl.lets_eat_together.user.User;

import java.util.List;
import java.util.Objects;

public class SingleOrderView extends VerticalLayout{

    Dialog dialog = new Dialog();
    Order order;

    CommentService commentService;
    UserModelService userModelService;

    List<Comment> comments;
    VerticalLayout commentViews = new VerticalLayout();

    SingleOrderView(CommentService commentService, UserModelService userModelService, Order order){
        this.order = order;
        this.commentService = commentService;
        this.userModelService = userModelService;

        updateList();
        configureList();
        createDialog();

        this.setAlignItems(Alignment.CENTER);
        add(new Html(("<span><b>Restaurant:</b> " + order.getRestaurant() +"</span>")));
        add(new Html("<span><b>Meal:</b> " + order.getMeal() +"</span>"));
        add(new Html("<span><b>Notes:</b> " + order.getNote() +"</span>"));
        add(new Html("<span><b>Call deadline:</b> " + order.getCallDeadline().toString() +"</span>"));
        add(new Html("<span><b>Status:</b> " + order.getStatus().toString() +"</span>"));
        add(new Html("<span><b>Office:</b> " + order.getOffice().toString() +"</span>"));
        add(new Html("<span><b>Pick up place:</b> " + order.getPickUpPlace() +"</span>"));
        add(new Html("<span><b>Payment methods:</b> " + order.getAllPaymentMethods() +"</span>"));



        Span email = new Span(order.getUser().getEmail());
        email.getStyle().set("width", "100%");
        Button addComment = new Button("Add your comment", e -> dialog.open());
        addComment.getStyle().set("width", "100%");
        addComment.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HorizontalLayout bottomBar = new HorizontalLayout(email, addComment);
        bottomBar.getStyle().set("width", "100%");
        if(Objects.equals(userDetails.getUsername(), order.getUser().getEmail())){
            Button editOrder = new Button("Edit order");
            editOrder.getStyle().set("width", "100%");
            bottomBar.add(editOrder);
        }else{
            Span span = new Span();
            bottomBar.getStyle().set("width", "100%");
            bottomBar.add(span);
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
    }

    private void updateList() {
        this.comments = this.order.getComments();
    }

    private void createDialog(){
        dialog.setHeaderTitle("Add a comment, your suborder");

        CommentForm commentForm = new CommentForm(this.commentService, this.userModelService, this.order);
        dialog.add(commentForm);

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);

        add(dialog);
    }

    private void configureList() {
        commentViews = new VerticalLayout();
        commentViews.setAlignItems(Alignment.CENTER);
        for (Comment comment: comments){
            commentViews.add(new SingleCommentView(comment));
        }
    }
}
