package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.Goods;

import java.util.List;
import java.util.Map;

/**
 * 商品表 服务接口类
 *
 * @author Chih
 * @date 2024/2/29 22:04
 */
public interface GoodsService {

    /**
     * 插入商品
     * @param goods 商品信息
     */
    void insertGoods(Goods goods);

    /**
     * 更新商品信息
     * @param goods 商品信息
     */
    void updateGoods(Goods goods);

    /**
     * 获取商品列表 分页
     * @param searchMap 查询参数
     * @return 商品列表
     */
    Map<String, Object> getGoodsList(Map<String, Object> searchMap);

    /**
     * 删除商品
     * @param goodsId 商品id
     */
    void deleteGoods(Long goodsId);

    Goods getGoodsById(Long goodsId);

    List<Goods> getValidGoodsList(Map<String, Object> searchMap);

    List<Goods> getGoodsByTag(String tag);

}
