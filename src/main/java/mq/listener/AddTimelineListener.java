package mq.listener;

import blog.entity.Blog;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timeline.serviceImpl.RealTimelineService;
import util.GsonUtil;

/**
 * Created by BASA on 2017/5/14.
 */


public class AddTimelineListener implements MessageListener{

    @Autowired
    private RealTimelineService timelineService;


    @Override
    public void onMessage(Message message) {

        String blog_json = new String( message.getBody() );
        try {
        Blog blog = GsonUtil.useGsonQueue().fromJson(blog_json, Blog.class);

        timelineService.addTimeline(blog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
