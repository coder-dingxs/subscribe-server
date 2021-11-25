package xyz.dingxs.subscribe.sniff.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.dingxs.subscribe.common.config.PortRangeConfig;
import xyz.dingxs.subscribe.common.constant.RedisConstant;
import xyz.dingxs.subscribe.subscribe.dto.SubscribeDto;
import xyz.dingxs.subscribe.subscribe.service.SubscribeService;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Set;

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

    @Autowired
    private PortRangeConfig portRangeConfig;


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
            logger.debug("start socket.connect");
            socket.connect(inetSocketAddress, 500);
            logger.debug("end socket.connect");
            res = true;
        } catch (Exception e) {
            logger.debug("end socket.connect", e);
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

    /**
     * 获取新端口
     *
     * @return 端口
     */
    public Integer generateNewPort() {

        Integer min = portRangeConfig.getMin();
        Integer max = portRangeConfig.getMax();

        // 生成端口
        Random random = new Random();
        Integer port = random.nextInt(max) % (max - min + 1) + min;
        Set<String> portSet = stringRedisTemplate.opsForSet().members(RedisConstant.PORT_SET);

        if (!CollectionUtils.isEmpty(portSet) && portSet.contains(port.toString())) {
            // 递归，重新生成
            return generateNewPort();
        } else {
            stringRedisTemplate.opsForSet().add(RedisConstant.PORT_SET, port.toString());
            return port;
        }
    }
}
