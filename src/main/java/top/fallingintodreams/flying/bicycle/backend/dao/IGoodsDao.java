package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;

import java.util.List;
import java.util.Map;

/**
 * 商品Dao
 *
 * @author Chih
 * @date 2024/2/28 23:28
 */
@Mapper
public interface IGoodsDao {
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
     * @return 商品列表
     */
    List<Goods> getGoodsList(Map<String, Object> searchMap);

    /**
     * 删除商品
     * @param goodsId 商品id
     */
    void deleteGoods(Long goodsId);

    /**
     * 获取商品
     * @param goodsId
     * @return
     */
    Goods getGoodsById(Long goodsId);

    List<Goods> getNormalGoodsList(Map<String, Object> searchMap);

}
