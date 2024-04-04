package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.Categorization;
import top.fallingintodreams.flying.bicycle.backend.service.CategorizationService;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategorizationController {

    @Resource
    private CategorizationService categorizationService;

    @PostMapping("/getCategoryList")
    public ApiResponse getCategoryList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> categoryList = categorizationService.getCategoryList(searchMap);
        return ApiResponse.success(categoryList);
    }

    @DeleteMapping("/deleteCategory")
    public ApiResponse deleteCategory(@RequestParam Integer categoryId) {
        categorizationService.deleteCategory(categoryId);
        return ApiResponse.success("删除成功");
    }

    @PostMapping("/updateCategory")
    public ApiResponse updateCategory(@RequestBody Categorization categorization) {
        categorizationService.updateCategory(categorization);
        return ApiResponse.success("更新成功");
    }

    @PostMapping("/insertCategory")
    public ApiResponse insertCategory(@RequestBody Categorization categorization) {
        categorizationService.insertCategory(categorization);
        return ApiResponse.success("更新成功");
    }

}
