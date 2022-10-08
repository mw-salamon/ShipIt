package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.UserModel;
import pl.lets_eat_together.repository.UserModelRepository;
import pl.lets_eat_together.user.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserModelService {

    private static final String USER_NOT_FOUND_MESSAGE = "username not found";
    private final UserModelRepository userModelRepository;

    @Autowired
    public UserModelService(@Qualifier("userModelRepository") UserModelRepository userModelRepository
                        ) {
        this.userModelRepository = userModelRepository;
    }

    public List<UserModel> getAllUserModels(){
        return userModelRepository.findAll();
    }

    public UserModel getUserModelById(Long id){
        Optional<UserModel> found = userModelRepository.findById(id);
        return found.orElseThrow();
    }

    public UserModel loadUserModelByUsername(String email) throws UsernameNotFoundException {
        return userModelRepository.findByEmail(email)
                             .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE)));
    }

//    public UserModel getUserModelByUsername(String username){
//        System.out.println(username);
//        System.out.println(username);
//        <Optional>User found = userRepository.findByUsername(username);
//        System.out.println(found);
//        UserModel userModel = new UserModel();
//        userModel.setFirstName(found.getFirstName());
//        userModel.setEmail(found.getEmail());
//        userModel.setSureName(found.getSureName());
//        userModel.setPassword(found.getPassword());
//        return userModelRepository.saveAndFlush(userModel);
//    }


    //TODO proper Exceptions classes

    public UserModel addUserModel(UserModel newUserModel){
        return userModelRepository.saveAndFlush(newUserModel);
    }


    public String deleteUserModel(Long id){
         UserModel userModel = getUserModelById(id);
         userModelRepository.delete(userModel);
         return "UserModel with id=" + id + " deleted";
    }

}
