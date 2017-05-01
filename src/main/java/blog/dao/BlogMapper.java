package blog.dao;

import blog.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by BASA on 2017/4/30.
 */
@Repository
public interface BlogMapper {

    /**
     * 插入一条博客
     */
    void insertBlog(Blog blog);


    /**
     * 获取博客列表
     * @param map
     * @return
     */
    List<Blog> getBlogs(Map<String, Object> map);


    /**
     * 得到一条博客的详细信息
     * @param blogId
     * @return
     */
    //@Cacheable(key = "'blogId' + #root.args[0]", value = "blogDetail")
    Blog getBlogDetail(@Param("blogId") int blogId);


    /**
     * 增加blog的likes数
     * @param blogID Blog的ID
     * @param number 增加的likes的数量
     */
    void incLikes(Map<String, Integer> updateMap);



    void deleteBlog(int blogId);

    String getUserName(int blogId);

}
