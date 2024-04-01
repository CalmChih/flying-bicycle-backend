package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.GoodsDTO;
import top.fallingintodreams.flying.bicycle.backend.po.Goods;
import top.fallingintodreams.flying.bicycle.backend.service.GoodsService;

import java.util.List;

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

    @GetMapping("/getGoodsList")
    public ApiResponse getGoodsList(@RequestParam int currentPage, @RequestParam int pageSize) {
        List<GoodsDTO> goodsList = goodsService.getGoodsList(currentPage, pageSize);
        return ApiResponse.success(goodsList);
    }

    @GetMapping("getGoodsTotal")
    public ApiResponse getGoodsTotal() {
        Integer goodsTotal = goodsService.getGoodsTotal();
        return ApiResponse.success(goodsTotal);
    }

    @GetMapping("/getGoodsInfoByTitle")
    public ApiResponse getGoodsInfo(@RequestParam String goodsTitle) {
        List<GoodsDTO> goodsInfo = goodsService.getGoodsInfo(goodsTitle);
        return ApiResponse.success(goodsInfo);
    }

}
