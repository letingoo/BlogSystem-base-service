package timeline.serviceImpl;

import blog.entity.Blog;
import follow.service.FollowService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import timeline.service.TimelineViewService;
import util.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by BASA on 2017/5/13.
 */
@Service
public class TimelineService implements TimelineViewService{

    //@Autowired
    private AmqpTemplate amqpTemplate;


    public void addTimeline(Blog blog) {

        amqpTemplate.convertAndSend("pushTimelineQueueKey", blog);
    }



    private static final int PAGE_SIZE = 10;


    @Autowired
    private Jedis jedis;


    private static final String TIME_LINE_PREFIX = "Timeline:";

    @Override
    public List<Blog> getTimeline(String userName, int pageNo) {

        jedis.auth("letingoo");

        int start = pageNo * PAGE_SIZE;
        int end = (pageNo + 1) * PAGE_SIZE - 1;

        List<Blog> result = new ArrayList<Blog>();

        Set<String> timeLineSet = jedis.zrevrange(TIME_LINE_PREFIX + userName, start, end);
        for (String timeLine : timeLineSet) {

            Blog blog = GsonUtil.useGson().fromJson(timeLine, Blog.class);
            result.add(blog);
        }

        return result;
    }
}
