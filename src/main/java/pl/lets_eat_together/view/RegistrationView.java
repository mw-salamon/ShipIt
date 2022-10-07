package pl.lets_eat_together.view;

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

        //TODO: add a button to log in for users who have accounts
    }
}

