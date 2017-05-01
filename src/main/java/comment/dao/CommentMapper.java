package comment.dao;

import comment.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by BASA on 2017/4/30.
 */
@Repository
public interface CommentMapper {

    void addComment(Comment comment);

    List<Comment> getComments(@Param("blogId") int blogId);
}
