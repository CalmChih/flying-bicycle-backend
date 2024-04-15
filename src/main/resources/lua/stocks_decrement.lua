--时间段内商品库存key
local stockKey = KEYS[1]
redis.log(redis.LOG_DEBUG,"key1=" ..stockKey)
-- 商品key
local goodsId = ARGV[1]
redis.log(redis.LOG_NOTICE, "stockKey:" ..stockKey)
redis.log(redis.LOG_NOTICE, "goodsId:" ..goodsId)


redis.log(redis.LOG_NOTICE, redis.call('HEXISTS', stockKey, goodsId))
redis.log(redis.LOG_NOTICE, tonumber(redis.call('HGET', stockKey, goodsId)))
-- 判断key是否存在
if (redis.call('HEXISTS', stockKey, goodsId) == 0) then
    -- key不存在，即库存不足
    return 0
end

-- key存在，判断库存是否充足
if (tonumber(redis.call('HGET', stockKey, goodsId)) <= 0) then
    -- 库存不足
    return 0
end

-- 扣减库存
redis.call('HINCRBY', stockKey, goodsId, -1)
return 1