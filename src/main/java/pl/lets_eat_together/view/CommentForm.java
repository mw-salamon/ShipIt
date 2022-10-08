package pl.lets_eat_together.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lets_eat_together.exception.MaxCommentException;
import pl.lets_eat_together.model.*;
import pl.lets_eat_together.service.*;
import pl.lets_eat_together.user.User;

import javax.transaction.Transactional;


public class CommentForm extends FormLayout {
    TextField meal = new TextField("Meal");
    TextArea notes = new TextArea("Notes");

    Button save = new Button("Save");
    CommentService commentService;
    UserModelService userModelService;
    Notification notification;
    Order order;


    public CommentForm(CommentService commentService, UserModelService userModelService, Order order){
        this.commentService = commentService;
        this.userModelService = userModelService;
        this.order = order;
        save.addClickListener(event -> saveComment());
        add(meal, notes, save);

    }

    @Transactional
    private void saveComment() {
        Comment newComment = new Comment();
        newComment.setMeal(meal.getValue());
        newComment.setNote(notes.getValue());
        newComment.setOrder(order);

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            UserModel userModel = userModelService.loadUserModelByUsername(userDetails.getUsername());
            newComment.setUser(userModel);
        }catch(UsernameNotFoundException e){
            UserModel userModel = new UserModel();
            userModel.setFirstName(userDetails.getFirstName());
            userModel.setEmail(userDetails.getEmail());
            userModel.setSureName(userDetails.getSureName());
            userModel.setPassword(userDetails.getPassword());
            userModelService.addUserModel(userModel);
            newComment.setUser(userModel);
        }


        try{
            commentService.addNewComment(newComment);
            notification = Notification.show("Udało się dodać komentarz");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().getPage().reload();
        }catch(MaxCommentException e){
            notification = Notification.show("Limit komentarzy został osiągnięty");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }catch(Exception e){
            notification = Notification.show("Nie udało się dodać komentarz");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            e.printStackTrace();
        }
    }
}
