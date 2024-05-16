--时间段内商品库存key
local stockKey = KEYS[1]

-- 商品key
local goodsId = ARGV[1]

-- 判断key是否存在
if (redis.call('HEXISTS', stockKey, goodsId) == 0) then
    -- key不存在，即库存不足
    return 0
end

-- 扣减库存
local afterNum = redis.call('HINCRBY', stockKey, goodsId, -1)

if (afterNum >= 0) then
    if (afterNum == 0) then
        redis.call('HDEL', stockKey, goodsId)
    end
    return 1
end

return 0