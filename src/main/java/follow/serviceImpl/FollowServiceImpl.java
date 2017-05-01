package follow.serviceImpl;

import follow.dao.FollowMapper;
import follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BASA on 2017/4/30.
 */

@Service
public class FollowServiceImpl implements FollowService{

    @Autowired
    private FollowMapper followMapper;


    @Override
    public String followSomeone(String follower, String followee) {
        try {

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("follower", follower);
            paramMap.put("followee", followee);

            if (followMapper.checkExist(paramMap) > 0)
                return "only once";

            followMapper.insertFollow(paramMap);
            return "success";

        } catch (Exception e) {
            return "fail";
        }
    }


    @Override
    public List<String> getFollowee(String follower) {
        return followMapper.getFollowee(follower);
    }
}
