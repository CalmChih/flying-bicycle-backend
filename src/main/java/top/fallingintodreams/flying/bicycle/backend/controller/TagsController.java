package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.Tags;
import top.fallingintodreams.flying.bicycle.backend.service.TagsService;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @GetMapping("/getTagList")
    public ApiResponse getTagList() {
        List<Tags> tagList = tagsService.getTagList();
        return ApiResponse.success(tagList);
    }

}
