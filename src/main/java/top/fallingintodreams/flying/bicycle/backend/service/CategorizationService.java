package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.Categorization;

import java.util.Map;

public interface CategorizationService {
    /**
     * 获取分类列表
     * @return 分类列表
     */
    Map<String, Object> getCategoryList(Map<String, Object> searchMap);

    /**
     * 获取分类id 分类名称 map
     * @return 分类id 分类名称 map
     */
    Map<Integer, String> getcategoryMap();

    /**
     * 删除分类
     * @param categoryId 分类id
     */
    void deleteCategory(Integer categoryId);

    /**
     * 更新分类
     * @param categorization 分类对象
     */
    void updateCategory(Categorization categorization);

    /**
     * 新增分类
     * @param categorization 分类对象
     */
    void insertCategory(Categorization categorization);

}
