package pl.lets_eat_together.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.lets_eat_together.registration.RegistrationForm;
import pl.lets_eat_together.registration.RegistrationService;

@Route("/")
public class RegistrationView extends VerticalLayout {

    public RegistrationView(RegistrationService registrationService) {

        RegistrationForm registrationForm = new RegistrationForm();
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, registrationService);
        registrationFormBinder.addBindingAndValidation();

        loginButton();

        add(loginButton());
    }

    private Button loginButton(){
        Button button = new Button("LOG IN");
        setHorizontalComponentAlignment(Alignment.CENTER, button);
        button.addClickListener( e -> UI.getCurrent().navigate("login"));
        return button;
    }
}

