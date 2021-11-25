package xyz.dingxs.subscribe.sniff.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.dingxs.subscribe.common.constant.RedisConstant;
import xyz.dingxs.subscribe.subscribe.dto.SubscribeDto;
import xyz.dingxs.subscribe.subscribe.service.SubscribeService;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author dingxs
 */
@Service
public class SniffService {

    private final Logger logger = LoggerFactory.getLogger(SniffService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SubscribeService subscribeService;

    /**
     * 获取嗅探结果
     *
     * @return 结果
     */
    public Boolean getSniffRes() {
        String res = stringRedisTemplate.opsForValue().get(RedisConstant.SNIFF_RES);
        return Boolean.parseBoolean(res);
    }

    /**
     * 嗅探
     *
     * @return 结果
     */
    public Boolean sniff() {
        SubscribeDto subscribeDto = subscribeService.getSubscribeDto();
        boolean res;
        try {
            Socket socket = new Socket();
            InetSocketAddress inetSocketAddress =
                    new InetSocketAddress(subscribeDto.getAdd(), Integer.parseInt(subscribeDto.getPort()));
            socket.connect(inetSocketAddress, 500);
            res = true;
        } catch (Exception e) {
            logger.error("sniff exception", e);
            res = false;
        }
        return res;
    }

    /**
     * 设置嗅探结果
     *
     * @param res 结果
     */
    public void setSniffRes(Boolean res) {
        stringRedisTemplate.opsForValue().set(RedisConstant.SNIFF_RES, res.toString());
    }

}
