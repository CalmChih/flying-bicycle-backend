package top.fallingintodreams.flying.bicycle.backend.common.mq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisMqConsumer {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IOrderDao orderDao;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 17,
            60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    @Scheduled(cron = "*/1 * * * * *")
    public void customer() {
        long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        Set<Object> orderIdSet = redisUtil.zRangeByScore(Constant.RedisKey.AUTO_CANCEL_ORDER_KEY, 0, epochMilli);
        log.info("当前时间：{}", LocalDateTime.now().getSecond());
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        orderIdSet.stream().map(Objects::toString).forEach(orderId -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    Order oder = new Order();
                    oder.setOrderId(orderId);
                    oder.setOrderState(Constant.OrderStateEnum.CANCELLED.getStateCode());
                    orderDao.updateOrder(oder);
                    redisUtil.zRemove(Constant.RedisKey.AUTO_CANCEL_ORDER_KEY, orderId);
                } catch (Exception e) {
                    log.error("取消订单【{}】失败，错误信息：{}", orderId, e.getMessage());
                }
            }, executor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
