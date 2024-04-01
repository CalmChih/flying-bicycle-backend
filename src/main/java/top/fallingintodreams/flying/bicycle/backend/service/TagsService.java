package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.Tags;

import java.util.List;

public interface TagsService {
    /**
     * 获取tag列表
     * @return tag列表
     */
    List<Tags> getTagList();

}
