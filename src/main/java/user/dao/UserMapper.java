package user.dao;

import org.springframework.stereotype.Repository;
import user.entity.User;

import java.util.HashMap;

/**
 * Created by BASA on 2017/4/30.
 */

@Repository
public interface UserMapper {

    /**
     * 添加用户
     */void addUser(User user);


    /**
     * 查找用户
     * @return
     */
    User selectUser(HashMap map);




    /**
     * 更新用户资料
     * @param user
     */
    void updateUser(User user);
}
