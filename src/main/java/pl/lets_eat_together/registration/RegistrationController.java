package pl.lets_eat_together.registration;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping("/")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token) + " \n Now you can click here to login: http://localhost:8080";
    }
}
