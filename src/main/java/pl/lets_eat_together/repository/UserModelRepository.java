package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.UserModel;
import pl.lets_eat_together.user.User;

import java.util.List;
import java.util.Optional;


@Repository("userModelRepository")
public interface UserModelRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
}