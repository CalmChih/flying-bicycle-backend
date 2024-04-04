package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.Tags;

import java.util.Map;

public interface TagsService {
    /**
     * 获取tag列表
     * @return tag列表
     */
    Map<String, Object> getTagList(Map<String, Object> searchMap);

    /**
     * 删除标签
     * @param TagId 标签id
     */
    void deleteTag(Long TagId);

    /**
     * 更新标签
     * @param tags 标签对象
     */
    void updateTag(Tags tags);

    /**
     * 新增标签
     * @param tags 标签对象
     */
    void insertTag(Tags tags);

}
