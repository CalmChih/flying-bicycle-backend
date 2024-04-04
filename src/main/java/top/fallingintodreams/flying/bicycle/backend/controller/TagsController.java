package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.Tags;
import top.fallingintodreams.flying.bicycle.backend.service.TagsService;

import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @PostMapping("/getTagList")
    public ApiResponse getTagList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> tagList = tagsService.getTagList(searchMap);
        return ApiResponse.success(tagList);
    }

    @DeleteMapping("/deleteTag")
    public ApiResponse deleteTag(@RequestParam Long tagId) {
        tagsService.deleteTag(tagId);
        return ApiResponse.success("删除成功");
    }

    @PostMapping("/updateTag")
    public ApiResponse updateTag(@RequestBody Tags tags) {
        tagsService.updateTag(tags);
        return ApiResponse.success("更新成功");
    }

    @PostMapping("/insertTag")
    public ApiResponse insertTag(@RequestBody Tags tags) {
        tagsService.insertTag(tags);
        return ApiResponse.success("更新成功");
    }

}
