package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.Tags;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITagsDao {

    /**
     * 获取tag列表
     * @return tag列表
     */
    List<Tags> getTagList(Map<String, Object> searchMap);

    void deleteTag(Long tagId);

    void updateTag(Tags tags);

    void insertTag(Tags tags);

}
