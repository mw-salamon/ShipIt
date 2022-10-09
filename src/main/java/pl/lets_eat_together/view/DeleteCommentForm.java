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
import pl.lets_eat_together.service.CommentService;
import pl.lets_eat_together.service.UserModelService;

public class DeleteCommentForm extends FormLayout {

    private TextField meal = new TextField("Meal");
    private TextArea notes = new TextArea("Notes");
    private Button delete = new Button("Delete");
    private CommentService commentService;
    private UserModelService userModelService;
    private Notification notification;
    private Order order;

    public DeleteCommentForm(CommentService commentService, UserModelService userModelService, Order order, Comment comment) {
        this.commentService = commentService;
        this.userModelService = userModelService;
        this.order = order;

        delete.addClickListener(event -> deleteComment(comment));

        add(delete);
    }

    protected void deleteComment(Comment comment) {
        try {
            commentService.deleteComment(comment.getId());
            notification = Notification.show("Udało się usunąć komentarz");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().getPage().reload();
        } catch(Exception e) {
            notification = Notification.show("Nie udało się usunąć komentarza");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }
    }
}