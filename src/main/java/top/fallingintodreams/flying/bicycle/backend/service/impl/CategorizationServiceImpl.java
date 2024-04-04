package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.ICategorizationDao;
import top.fallingintodreams.flying.bicycle.backend.po.Categorization;
import top.fallingintodreams.flying.bicycle.backend.service.CategorizationService;
import top.fallingintodreams.flying.bicycle.backend.util.IdUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategorizationServiceImpl implements CategorizationService {

    @Resource
    private ICategorizationDao categorizationDao;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> getCategoryList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<Categorization> categoryList = categorizationDao.getCategoryList(searchMap);
        PageInfo<Categorization> pageInfo = new PageInfo<>(categoryList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", categoryList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Integer, String> getcategoryMap() {
        if (redisUtil.get(Constant.RedisKey.CATEGORY_MAP_KEY) != null) {
            return (Map<Integer, String>) JSON.parseObject(
                    redisUtil.get(Constant.RedisKey.CATEGORY_MAP_KEY).toString(), Map.class);
        }
        List<Categorization> categoryList = categorizationDao.getCategoryList(null);
        Map<Integer, String> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(Categorization::getCategoryId, Categorization::getCategoryName));
        redisUtil.set(Constant.RedisKey.CATEGORY_MAP_KEY, JSON.toJSONString(categoryMap), 60 * 5L);
        return categoryMap;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categorizationDao.deleteCategory(categoryId);
    }

    @Override
    public void updateCategory(Categorization categorization) {
        categorizationDao.updateCategory(categorization);
    }

    @Override
    public void insertCategory(Categorization categorization) {
        categorization.setCategoryId(IdUtil.generateCategoryId());
        categorizationDao.insertCategory(categorization);
    }
}
