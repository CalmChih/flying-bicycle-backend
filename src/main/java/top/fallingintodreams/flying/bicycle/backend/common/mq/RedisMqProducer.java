package top.fallingintodreams.flying.bicycle.backend.common.mq;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;

@Component
public class RedisMqProducer {

    @Resource
    private RedisUtil redisUtil;

    public void send(String orderId, long delay) {
        redisUtil.zSet(Constant.RedisKey.AUTO_CANCEL_ORDER_KEY, orderId, delay);
    }

}
