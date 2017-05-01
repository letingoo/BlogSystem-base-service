package follow.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by BASA on 2017/4/30.
 */
@Repository
public interface FollowMapper {

    void insertFollow(Map<String, String> followMap);

    List<String> getFollowee(String follower);

    List<String> getFollower(String followee);

    int checkExist(Map<String, String> paramMap);
}
