package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;

import java.util.List;

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
    List<Goods> getGoodsList();

    /**
     * 获取商品总数
     * @return 商品总数
     */
    Integer getGoodsTotal();

    /**
     * 获取商品详情
     * @param goodsTitle 商品名称
     * @return 商品详情
     */
    List<Goods> getGoodsInfo(String goodsTitle);

}
