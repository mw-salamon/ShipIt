package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.Office;
import pl.lets_eat_together.model.Order;


@Repository("officeRepository")
public interface OfficeRepository extends JpaRepository<Office, Long> {

}