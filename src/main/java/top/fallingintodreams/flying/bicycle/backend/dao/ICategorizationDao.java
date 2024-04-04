package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.Categorization;

import java.util.List;
import java.util.Map;

@Mapper
public interface ICategorizationDao {

    /**
     * 获取分类列表
     * @return 分类列表
     */
    List<Categorization> getCategoryList(Map<String, Object> searchMap);

    void deleteCategory(Integer categoryId);

    void updateCategory(Categorization categorization);

    void insertCategory(Categorization categorization);

}
