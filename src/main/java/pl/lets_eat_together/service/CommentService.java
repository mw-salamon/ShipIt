package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.exception.MaxCommentException;
import pl.lets_eat_together.model.Comment;
import pl.lets_eat_together.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("commentService")
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

//    public List<Comment> getAllCommentsForOrder(Long orderId){
//        return commentRepository.findAllByOrderId(orderId);
//    }

    public Comment getCommentById(Long id){
        Optional<Comment> found = commentRepository.findById(id);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public Comment addNewComment(Comment newComment) throws MaxCommentException {
        if (newComment.getOrder().getMaxComments() > (long) newComment.getOrder().getComments().size()){
            return commentRepository.saveAndFlush(newComment);
        }else{
            throw new MaxCommentException();
        }
    }

    @Transactional
    public Comment updateComment(Comment comment){
        Optional<Comment> foundComment = commentRepository.findById(comment.getId());
        if(foundComment.isEmpty()){
            throw new IllegalStateException("Comment doesn't exist, try again");
        }
        Comment commentFromRepository = foundComment.get();
        commentFromRepository.setMeal(comment.getMeal());
        commentFromRepository.setNote(comment.getNote());
        return commentRepository.save(commentFromRepository);
    }

    @Transactional
    public String deleteComment(Long id){
         Comment comment = getCommentById(id);
         commentRepository.delete(comment);
         return "Comment with id=" + id + " deleted";
    }


}
