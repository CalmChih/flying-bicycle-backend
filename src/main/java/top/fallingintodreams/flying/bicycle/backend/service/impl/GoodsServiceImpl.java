package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IGoodsDao;
import top.fallingintodreams.flying.bicycle.backend.dto.GoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;
import top.fallingintodreams.flying.bicycle.backend.service.CategorizationService;
import top.fallingintodreams.flying.bicycle.backend.service.GoodsService;
import top.fallingintodreams.flying.bicycle.backend.util.IdUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品表 服务实现类
 *
 * @author Chih
 * @date 2024/2/29 22:05
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private IGoodsDao goodsDao;

    @Resource
    private CategorizationService categorizationService;

    @Override
    public void insertGoods(Goods goods) {
        Assert.notNull(goods, "商品信息不能为空");
        goods.setGoodsId(IdUtil.generateGoodsId());
        goodsDao.insertGoods(goods);
    }

    @Override
    public void updateGoods(Goods goods) {
        Assert.notNull(goods, "商品信息不能为空");
        goodsDao.updateGoods(goods);
    }

    @Override
    public List<GoodsDTO> getGoodsList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Goods> goodsList = goodsDao.getGoodsList();
        List<GoodsDTO> result = new ArrayList<>();
        Map<Integer, String> categoryMap = categorizationService.getcategoryMap();
        goodsList.forEach(goods -> result.add(this.vo2Dto(goods, categoryMap)));
        return result;
    }

    @Override
    public Integer getGoodsTotal() {
        return goodsDao.getGoodsTotal();
    }

    @Override
    public List<GoodsDTO> getGoodsInfo(String goodsTitle) {
        List<Goods> goodsInfo = goodsDao.getGoodsInfo(goodsTitle);
        Map<Integer, String> categoryMap = categorizationService.getcategoryMap();
        List<GoodsDTO> result = new ArrayList<>();
        goodsInfo.forEach(goods -> result.add(vo2Dto(goods, categoryMap)));
        return result;
    }

    private GoodsDTO vo2Dto(Goods goodsInfo, Map<Integer, String> categoryMap) {
        GoodsDTO goodsDTO = new GoodsDTO();
        if (goodsInfo == null) {
            return goodsDTO;
        }
        goodsDTO.setGoodsId(goodsInfo.getGoodsId());
        goodsDTO.setGoodsTitle(goodsInfo.getGoodsTitle());
        goodsDTO.setGoodsDesc(goodsInfo.getGoodsDesc());
        goodsDTO.setGoodsNum(goodsInfo.getGoodsNum());
        goodsDTO.setCategoryId(goodsInfo.getCategoryId());
        goodsDTO.setCategoryName(categoryMap.get(goodsInfo.getCategoryId()));
        goodsDTO.setGoodsTags(goodsInfo.getGoodsTags());
        goodsDTO.setGoodsImg(goodsInfo.getGoodsImg());
        goodsDTO.setState(goodsInfo.getState());
        goodsDTO.setStateStr(Constant.goodsStateMap.get(goodsInfo.getState()));
        return goodsDTO;
    }

}
