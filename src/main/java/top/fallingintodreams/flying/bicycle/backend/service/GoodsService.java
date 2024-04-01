package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.dto.GoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;

import java.util.List;

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
     *
     * @param currentPage 当前页码
     * @param pageSize    数量
     * @return 商品列表
     */
    List<GoodsDTO> getGoodsList(int currentPage, int pageSize);

    /**
     * 获取商品总数
     * @return 商品总数
     */
    Integer getGoodsTotal();

    /**
     * 获取商品详情
     *
     * @param goodsTitle 商品名称
     * @return 商品详情
     */
    List<GoodsDTO> getGoodsInfo(String goodsTitle);

}
