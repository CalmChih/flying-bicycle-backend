package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.Categorization;
import top.fallingintodreams.flying.bicycle.backend.service.CategorizationService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategorizationController {

    @Resource
    private CategorizationService categorizationService;

    @GetMapping("/getCategoryList")
    public ApiResponse getCategoryList() {
        List<Categorization> categoryList = categorizationService.getCategoryList();
        return ApiResponse.success(categoryList);
    }

}
