import blog.entity.Blog;
import blog.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.service.UserService;

import javax.annotation.Resource;

/**
 * Created by BASA on 2017/5/1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class TestSpring {


    @Resource(name = "userService")
    private UserService userService;

    @Test
    public void testSpring() {

        String name = "test";
        String pass = "123";

        userService.doLogin(name, pass);
    }

}
