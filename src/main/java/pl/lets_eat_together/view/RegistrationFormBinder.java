package pl.lets_eat_together.view;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import pl.lets_eat_together.registration.RegistrationForm;
import pl.lets_eat_together.registration.RegistrationRequest;
import pl.lets_eat_together.registration.RegistrationService;
import pl.lets_eat_together.user.User;

public class RegistrationFormBinder {

    private RegistrationForm registrationForm;
    private RegistrationService registrationService;

    private boolean enablePasswordValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm, RegistrationService registrationService) {
        this.registrationForm = registrationForm;
        this.registrationService = registrationService;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

        // The bindInstanceFields method facilitates automatic data binding and validation:
        // it automatically matches the fields of the RegistrationForm object to the
        // properties of the UserDetails object based on their names.
        binder.bindInstanceFields(registrationForm);

        // A custom validator for password fields to handle password-confirmation logic
        binder.forField(registrationForm.getPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        // The second password field is not connected to the Binder, but we
        // want the binder to re-check the password validator when the field
        // value changes. The easiest way is just to do that manually.
        registrationForm.getPasswordConfirmField().addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            binder.validate();
        });


        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(event -> {
            try {

                User user = new User();

                binder.writeBean(user);

                registrationService.register(new RegistrationRequest(
                        user.getFirstName(),
                        user.getSureName(),
                        user.getEmail(),
                        user.getPassword()));

                showSuccess(user);

            } catch (ValidationException | IllegalStateException exception) {
                Notification notification = Notification.show("Register your account using Capgemini email");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (pass1 == null || pass1.length() < 3) {
            return ValidationResult.error("Password should be at least 3 characters long");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registrationForm.getPasswordConfirmField().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private void showSuccess(User userBean) {
        Notification notification =
                Notification.show("Thanks for joining us, " + userBean.getFirstName() + "! Check your email!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // TODO: redirect the user to another view
    }
}