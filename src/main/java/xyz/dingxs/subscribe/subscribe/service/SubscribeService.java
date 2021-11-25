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

import java.nio.charset.StandardCharsets;
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
        // 解析
        SubscribeDto subscribeDto = this.parse(url);
        // 校验url
        if (this.check(subscribeDto)) {

            String json = this.parseJson(subscribeDto);
            byte[] encode = Base64.getEncoder().encode(url.getBytes(StandardCharsets.UTF_8));
            // 存两个
            stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_URL, new String(encode));
            assert json != null;
            stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_DTO, json);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从redis取出直接返回
     *
     * @return json
     */
    public String getConfig() {
        return stringRedisTemplate.opsForValue().get(RedisConstant.SUBSCRIBE_DTO);
    }

    /**
     * 校验subscribeDto
     *
     * @param subscribeDto subscribeDto
     * @return 校验结果
     */
    private Boolean check(SubscribeDto subscribeDto) {
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
        return !ObjectUtils.isEmpty(subscribeDto.getNet());
    }

    /**
     * 解析url到java-dto
     *
     * @param url url
     * @return dto
     */
    private SubscribeDto parse(String url) {
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

    /**
     * 从redis取出后转换为dto返回
     *
     * @return SubscribeDto
     */
    public SubscribeDto getSubscribeDto() {
        String config = this.getConfig();
        try {
            return objectMapper.readValue(config, SubscribeDto.class);
        } catch (Exception e) {
            logger.error("getSubscribeDto Exception", e);
            return null;
        }
    }

    /**
     * dto to json
     *
     * @param subscribeDto dto
     * @return json
     */
    private String parseJson(SubscribeDto subscribeDto) {
        try {
            return objectMapper.writeValueAsString(subscribeDto);
        } catch (Exception e) {
            logger.error("parseJson Exception", e);
            return null;
        }
    }

    /**
     * 修改端口，url和dto
     *
     * @param port 端口
     * @return 成功
     */
    public Boolean changePort(Integer port) {
        SubscribeDto subscribeDto = this.getSubscribeDto();
        subscribeDto.setPort(port.toString());

        String json = this.parseJson(subscribeDto);
        assert json != null;
        byte[] jsonEncode = Base64.getEncoder().encode(json.getBytes(StandardCharsets.UTF_8));
        String url = "vmess://" + new String(jsonEncode);
        byte[] urlEncode = Base64.getEncoder().encode(url.getBytes(StandardCharsets.UTF_8));

        stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_URL, new String(urlEncode));
        stringRedisTemplate.opsForValue().set(RedisConstant.SUBSCRIBE_DTO, json);

        return true;
    }
}
