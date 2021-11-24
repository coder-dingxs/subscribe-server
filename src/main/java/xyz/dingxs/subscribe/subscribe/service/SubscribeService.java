package xyz.dingxs.subscribe.subscribe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import xyz.dingxs.subscribe.common.constant.RedisConstant;
import xyz.dingxs.subscribe.subscribe.dto.SubscribeDto;

import java.util.Base64;

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

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * get url
     *
     * @return url
     */
    public String get() {
        return stringRedisTemplate.opsForValue().get(RedisConstant.SUBSCRIBE_URL);
    }

    /**
     * set url
     *
     * @param url url
     * @return 是否成功
     */
    public Boolean set(String url) {
        // 校验url
        if (this.checkUrl(url)) {
            // 存
            stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_URL, url);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 校验url
     *
     * @param url url
     * @return 校验结果
     */
    public Boolean checkUrl(String url) {
        SubscribeDto subscribeDto = this.parse(url);
        if (ObjectUtils.isEmpty(subscribeDto.getAdd())) {
            return false;
        }
        if (ObjectUtils.isEmpty(subscribeDto.getPort())) {
            return false;
        }
        if (ObjectUtils.isEmpty(subscribeDto.getId())) {
            return false;
        }
        if (ObjectUtils.isEmpty(subscribeDto.getAid())) {
            return false;
        }
        if (ObjectUtils.isEmpty(subscribeDto.getNet())) {
            return false;
        }
        return true;
    }

    public SubscribeDto parse(String url) {
        SubscribeDto subscribeDto = null;
        try {
            url = url.substring(8);
            byte[] urlByteArray = Base64.getDecoder().decode(url);
            subscribeDto = objectMapper.readValue(urlByteArray, SubscribeDto.class);
        } catch (Exception e) {
            logger.error("url parse dto exception", e);
        }
        return subscribeDto;
    }

}
