package xyz.dingxs.subscribe.subscribe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.dingxs.subscribe.common.constant.RedisConstant;

/**
 * 订阅service
 *
 * @author dingxs
 */
@Service
public class SubscribeService {

    private final Logger logger = LoggerFactory.getLogger(SubscribeService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * get url
     *
     * @return url
     */
    public String get() {
        String url = stringRedisTemplate.opsForValue().get(RedisConstant.SUBSCRIBE_URL);
        if (url == null || "".equals(url)) {
            url = "";
        }
        return url;
    }

    /**
     * set url
     *
     * @param url url
     * @return 是否成功
     */
    public Boolean set(String url) {
        // todo 校验url

        // 存
        stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_URL, url);
        return true;
    }
}
