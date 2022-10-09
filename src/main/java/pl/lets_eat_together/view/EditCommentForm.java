package pl.lets_eat_together.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import pl.lets_eat_together.model.Comment;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.service.*;

public class EditCommentForm extends FormLayout {

    private TextField meal = new TextField("Meal");
    private TextArea notes = new TextArea("Notes");
    private Button save = new Button("Save");
    private CommentService commentService;
    private UserModelService userModelService;
    private Notification notification;
    private Order order;

    public EditCommentForm(CommentService commentService, UserModelService userModelService, Order order, Comment comment) {
        this.commentService = commentService;
        this.userModelService = userModelService;
        this.order = order;

        meal.setValue(comment.getMeal());
        notes.setValue(comment.getNote());

        save.addClickListener(event -> updateComment(comment));
        add(meal, notes, save);
    }

    private void updateComment(Comment comment){
        comment.setMeal(meal.getValue());
        comment.setNote(notes.getValue());
        comment.setOrder(order);

        try {
            commentService.updateComment(comment);
            notification = Notification.show("Udało się edytować komentarza");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().getPage().reload();
        } catch(Exception e) {
            notification = Notification.show("Nie udało się edytować komentarza");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }
    }
}