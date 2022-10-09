package pl.lets_eat_together.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.lets_eat_together.model.Comment;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.security.SecurityService;
import pl.lets_eat_together.service.CommentService;
import pl.lets_eat_together.service.UserModelService;

public class SingleCommentView extends VerticalLayout{

    private static final Logger logger = LoggerFactory.getLogger(SingleCommentView.class);

    private Dialog editCommentDialog = new Dialog();
    private ConfirmDialog deleteCommentDialog = new ConfirmDialog();

    private CommentService commentService;
    private UserModelService userModelService;
    private Order order;

    SingleCommentView(CommentService commentService, UserModelService userModelService, Order order, Comment comment){

        this.commentService = commentService;
        this.userModelService = userModelService;
        this.order = order;

        createEditCommentDialog(comment);
        createDeleteCommentDialog(comment);

        this.setAlignItems(Alignment.CENTER);
        add(new Html("<span><b>Meal:</b> " + comment.getMeal() +"</span>"));
        add(new Html("<span><b>Notes:</b> " + comment.getNote() +"</span>"));

        if(ifUserCreatedThisOrder(comment) || ifUserCreatedThisComment(comment)) {
            add(new Html("<span><b>Email:</b> " + comment.getUser().getEmail() +"</span>"));
        }
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.getStyle().set("width", "100%");

        if(ifUserCreatedThisComment(comment)) {
            Button editButton = new Button("Edit your order");
            Button deleteButton = new Button("Delete your order");

            editButton.getStyle().set("width", "100%");
            deleteButton.getStyle().set("width", "100%");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            editButton.addClickListener(event -> editCommentDialog.open());
            deleteButton.addClickListener(event -> deleteCommentDialog.open());

            horizontalLayout.add(editButton, deleteButton);
        }
        add(horizontalLayout);

        setSpacing(false);
        setPadding(true);
        getStyle().set("border", "1px solid var(--lumo-success-color)" );
        getStyle().set("border-radius", "var(--lumo-border-radius-l)");
    }
    private static boolean ifUserCreatedThisComment(Comment comment) {
        return comment.getUser().getEmail().equals(SecurityService.getAuthenticatedUser().getEmail());
    }

    private static boolean ifUserCreatedThisOrder(Comment comment) {
        return comment.getOrder().getUser().getEmail().equals(SecurityService.getAuthenticatedUser().getEmail());
    }

    private void createEditCommentDialog(Comment comment){
        editCommentDialog.setHeaderTitle("Edit your comment");
        editCommentDialog.setWidth("40em");

        EditCommentForm editCommentForm = new EditCommentForm(commentService, userModelService, order, comment);
        editCommentDialog.add(editCommentForm);

        Button cancelButton = new Button("Cancel", e -> editCommentDialog.close());
        editCommentDialog.getFooter().add(cancelButton);

        add(editCommentDialog);
    }

    private void createDeleteCommentDialog(Comment comment){
        deleteCommentDialog.setHeader("Delete your order");
        deleteCommentDialog.setText("Are you sure that you want to delete your order?");
        deleteCommentDialog.setWidth("40em");

        deleteCommentDialog.setCancelable(true);
        deleteCommentDialog.addCancelListener(click -> deleteCommentDialog.close());

        deleteCommentDialog.setConfirmText("Delete");
        deleteCommentDialog.addConfirmListener(click -> new DeleteCommentForm(commentService, userModelService, order, comment).deleteComment(comment));
    }
}
