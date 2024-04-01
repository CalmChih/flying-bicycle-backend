package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.fallingintodreams.flying.bicycle.backend.dao.ITagsDao;
import top.fallingintodreams.flying.bicycle.backend.po.Tags;
import top.fallingintodreams.flying.bicycle.backend.service.TagsService;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Resource
    private ITagsDao tagsDao;

    @Override
    public List<Tags> getTagList() {
        return tagsDao.getTagList();
    }

}
