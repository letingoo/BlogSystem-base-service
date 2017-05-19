package timeline.serviceImpl;

import blog.entity.Blog;
import follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import util.GsonUtil;

import java.util.List;

/**
 * Created by BASA on 2017/5/14.
 */

@Service
public class RealTimelineService {

    @Autowired
    private FollowService followService;


    @Autowired
    private Jedis jedis;



    private static final String TIME_LINE_PREFIX = "Timeline:";


    public void addTimeline(Blog blog) {

        try {
            String leader = blog.getUserName();
            List<String> followers = followService.getFollower(leader);

            jedis.auth("letingoo");
            for (String follower : followers) {
                jedis.zadd(TIME_LINE_PREFIX + follower, blog.getTime().getTime(), GsonUtil.useGson().toJson(blog));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
