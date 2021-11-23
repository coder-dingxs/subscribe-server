package xyz.dingxs.subscribe.common.constant;

/**
 * redis常量类
 *
 * @author dingxs
 */
public class RedisConstant {

    private RedisConstant() {
    }

    /**
     * 订阅url redis-key
     */
    public static final String SUBSCRIBE_URL = "subscribe:url";

    /**
     * 嗅探结果 redis-key
     */
    public static final String SNIFF_RES = "sniff:res";


}
