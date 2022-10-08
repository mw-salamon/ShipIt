package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.Comment;

import java.util.List;


@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("SELECT * FROM comments c WHERE c.order_id=:orderId")
//    List<Comment>  findAllByOrderId(Long orderId);
}