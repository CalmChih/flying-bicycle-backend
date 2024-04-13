package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.HashMap;
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
    public Map<String, Object> getGoodsList(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();
        int currentPage = (int) searchMap.get("currentPage");
        int pageSize = (int) searchMap.get("pageSize");
        PageHelper.startPage(currentPage, pageSize);
        List<Goods> goodsList = goodsDao.getGoodsList(searchMap);
        PageInfo<Goods> page = new PageInfo<>(goodsList);
        long total = page.getTotal();
        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        Map<Integer, String> categoryMap = categorizationService.getcategoryMap();
        goodsList.forEach(goods -> goodsDTOList.add(this.vo2Dto(goods, categoryMap)));
        resultMap.put("list", goodsDTOList);
        resultMap.put("total", total);
        return resultMap;
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
        goodsDTO.setGoodsPrice(goodsInfo.getGoodsPrice());
        goodsDTO.setCategoryId(goodsInfo.getCategoryId());
        goodsDTO.setCategoryName(categoryMap.get(goodsInfo.getCategoryId()));
        goodsDTO.setGoodsTags(goodsInfo.getGoodsTags());
        goodsDTO.setGoodsImg(goodsInfo.getGoodsImg());
        goodsDTO.setState(goodsInfo.getState());
        goodsDTO.setStateStr(Constant.goodsStateMap.get(goodsInfo.getState()));
        return goodsDTO;
    }

    @Override
    public void deleteGoods(Long goodsId) {
        goodsDao.deleteGoods(goodsId);
    }

    @Override
    public Goods getGoodsById(Long goodsId) {
        return goodsDao.getGoodsById(goodsId);
    }
}
