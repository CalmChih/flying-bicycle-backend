package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.Categorization;

import java.util.List;
import java.util.Map;

public interface CategorizationService {
    /**
     * 获取分类列表
     * @return 分类列表
     */
    List<Categorization> getCategoryList();

    /**
     * 获取分类id 分类名称 map
     * @return 分类id 分类名称 map
     */
    Map<Integer, String> getcategoryMap();

}
