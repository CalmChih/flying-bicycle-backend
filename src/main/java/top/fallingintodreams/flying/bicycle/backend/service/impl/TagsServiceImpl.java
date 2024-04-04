package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.fallingintodreams.flying.bicycle.backend.dao.ITagsDao;
import top.fallingintodreams.flying.bicycle.backend.po.Tags;
import top.fallingintodreams.flying.bicycle.backend.service.TagsService;
import top.fallingintodreams.flying.bicycle.backend.util.IdUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagsServiceImpl implements TagsService {

    @Resource
    private ITagsDao tagsDao;

    @Override
    public Map<String, Object> getTagList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<Tags> tagList = tagsDao.getTagList(searchMap);
        PageInfo<Tags> pageInfo = new PageInfo<>(tagList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", tagList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

    @Override
    public void deleteTag(Long TagId) {
        tagsDao.deleteTag(TagId);
    }

    @Override
    public void updateTag(Tags tags) {
        tagsDao.updateTag(tags);
    }

    @Override
    public void insertTag(Tags tags) {
        tags.setTagId(IdUtil.generateTagId());
        tagsDao.insertTag(tags);
    }

}
