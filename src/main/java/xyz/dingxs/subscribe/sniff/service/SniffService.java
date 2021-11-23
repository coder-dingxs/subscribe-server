package xyz.dingxs.subscribe.sniff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.dingxs.subscribe.common.constant.RedisConstant;

/**
 * @author dingxs
 */
@Service
public class SniffService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        // todo 一些网络相关代码
        return true;
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
