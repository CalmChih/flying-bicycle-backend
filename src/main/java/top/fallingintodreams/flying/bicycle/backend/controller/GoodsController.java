package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;
import top.fallingintodreams.flying.bicycle.backend.service.GoodsService;

import java.util.List;
import java.util.Map;

/**
 * 商品Controller
 *
 * @author Chih
 * @date 2024/2/29 22:24
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping("/insertGoods")
    public ApiResponse insertGoods(@RequestBody Goods goods) {
        goodsService.insertGoods(goods);
        return ApiResponse.success("添加成功");
    }

    @PostMapping("/updateGoods")
    public ApiResponse updateGoods(@RequestBody Goods goods) {
        goodsService.updateGoods(goods);
        return ApiResponse.success("更新成功");
    }

    @PostMapping("/getGoodsList")
    public ApiResponse getGoodsList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> goodsList = goodsService.getGoodsList(searchMap);
        return ApiResponse.success(goodsList);
    }

    @DeleteMapping("/deleteGoods")
    public ApiResponse deleteGoods(@RequestParam Long goodsId) {
        goodsService.deleteGoods(goodsId);
        return ApiResponse.success("删除成功");
    }

    @GetMapping("/getGoodsById")
    public ApiResponse getGoodsById(@RequestParam Long goodsId) {
        Goods goods = goodsService.getGoodsById(goodsId);
        return ApiResponse.success(goods);
    }

    @PostMapping("/getValidGoodsList")
    public ApiResponse getValidGoodsList(@RequestBody Map<String, Object> searchMap) {
        List<Goods> validGoodsList = goodsService.getValidGoodsList(searchMap);
        return ApiResponse.success(validGoodsList);
    }

    @GetMapping("/getGoodsByTag")
    public ApiResponse getGoodsByTag(@RequestParam String tag) {
        List<Goods> goodsList = goodsService.getGoodsByTag(tag);
        return ApiResponse.success(goodsList);
    }

}
