package blog.serviceImpl;

import blog.dao.BlogMapper;
import blog.entity.Blog;
import blog.service.BlogService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import timeline.serviceImpl.TimelineService;
import util.PageParam;
import util.UrlUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BASA on 2017/4/30.
 */
@Service
public class BlogServiceImpl implements BlogService {




    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private Jedis jedis;



    @Override
    public void addBlog(Blog blog) {

        // 将blog内容中的url转换成short url
        longUrlToShortUrl(blog);

        blogMapper.insertBlog(blog);


        // 把blog推送到timeline系统上
        //timelineService.addTimeline(blog);
    }


    @Override
    public String deleteBlog(int blogId, String deleter) {

        String userName = blogMapper.getUserName(blogId);
        if (!userName.equals(deleter))
            return "You can't delete it";


        blogMapper.deleteBlog(blogId);
        return "delete success";
    }


    @Override
    public List<Blog> getBlogs(PageParam pageParam) {

        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("pageParam", pageParam);
        searchMap.put( "blog", new Blog() );

        return blogMapper.getBlogs(searchMap);
    }

    @Override
    public List<Blog> getBlogsByUser(PageParam pageParam, String userName) {

        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("pageParam", pageParam);
        Blog blog = new Blog();
        blog.setUserName( userName );
        searchMap.put("blog", blog);

        List<Blog> result = blogMapper.getBlogs(searchMap);
        return result;
    }


    @Override
    public Blog getBlogDetail(int blogId) {

        Blog blog = blogMapper.getBlogDetail(blogId);

        int db_likes = blog.getLikes();

        // 从Redis中读取likes值
        BoundZSetOperations<String, Integer> boundZSetOperations = redisTemplate.boundZSetOps("likes");

        Double score = boundZSetOperations.score(blogId);
        if (score != null) {
            int cache_likes = score.intValue();
            blog.setLikes( db_likes + cache_likes );
        }

        else
            blog.setLikes( db_likes );

        return blog;
    }




    /**
     * 阈值，大于该阈值写入MySql
     */
    private static final int LIKES_ENOUGH = 10;

    @Override
    public String likeBlog(int blogId) {

        BoundZSetOperations<String, Integer> boundZSetOperations = redisTemplate.boundZSetOps("likes");
        if (null == boundZSetOperations.score(blogId))
            boundZSetOperations.add(blogId, 1);
        else {
            int redis_likes = boundZSetOperations.score(blogId).intValue();
            if (redis_likes >= LIKES_ENOUGH) {
                boundZSetOperations.remove(blogId);
                Map<String, Integer> updateMap = new HashMap<String, Integer>();
                updateMap.put("blogId", blogId);
                updateMap.put("likes", redis_likes + 1);

                blogMapper.incLikes(updateMap);
            }

            else {
                boundZSetOperations.incrementScore(blogId, 1);
            }
        }


        return "success";
    }


    private void longUrlToShortUrl(Blog blog) {

        String content = blog.getContent();

        String regex = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String url = matcher.group();
            String shortUrl = realLongUrlToShortUrl(url);
            matcher.appendReplacement(result, shortUrl);
        }
        matcher.appendTail(result);
        blog.setContent(result.toString());
    }


    private static final String URL_INDEX = "url_index";
    private static final String URL_SET = "url_set";
    private static final String TRANSFER_PREFIX = "http://114.215.159.226:18080/shortUrl/";


    private String realLongUrlToShortUrl(String url) {

        jedis.auth("letingoo");

        long url_id = jedis.incr(URL_INDEX);
        String transUrl = UrlUtil.toShortUrl(url_id);

        jedis.hset(URL_SET, transUrl, url);

        return TRANSFER_PREFIX + transUrl;

    }



}
