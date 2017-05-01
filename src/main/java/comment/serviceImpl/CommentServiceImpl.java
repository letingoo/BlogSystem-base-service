package comment.serviceImpl;

import comment.dao.CommentMapper;
import comment.entity.Comment;
import comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by BASA on 2017/4/30.
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper mapper;

    @Override
    public List<Comment> getComments(int blogId) {

        return mapper.getComments(blogId);
    }



    @Override
    public void addComment(Comment comment) {
        mapper.addComment(comment);
    }
}
