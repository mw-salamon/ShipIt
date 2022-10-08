package pl.lets_eat_together.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.lets_eat_together.model.Comment;

public class SingleCommentView extends VerticalLayout{


    SingleCommentView( Comment comment){
        this.setAlignItems(Alignment.CENTER);
        add(new Html("<span><b>Meal:</b> " + comment.getMeal() +"</span>"));
        add(new Html("<span><b>Notes:</b> " + comment.getNote() +"</span>"));

        Span email = new Span(comment.getUser().getEmail());
//        Button addComment = new Button("Add your comment", e -> dialog.open());
        HorizontalLayout bottomBar = new HorizontalLayout(email);
        add(bottomBar);

        setSpacing(false);
        setPadding(true);
        getStyle().set("border", "1px solid var(--lumo-success-color)" );
        getStyle().set("border-radius", "var(--lumo-border-radius-l)");
    }
}
