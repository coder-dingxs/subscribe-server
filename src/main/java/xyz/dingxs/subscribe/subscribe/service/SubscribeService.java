package xyz.dingxs.subscribe.subscribe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
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
     * 从redis取出直接返回
     *
     * @return json
     */
    public String getConfig() {
        return stringRedisTemplate.opsForValue().get(RedisConstant.SUBSCRIBE_DTO);
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
