package top.fallingintodreams.flying.bicycle.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.service.AfterSalesService;

import java.util.Map;

@RestController
@RequestMapping("/afterSales")
public class AfterSalesController {

    @Resource
    private AfterSalesService afterSalesService;

    @PostMapping("/insertAfterSalesInfo")
    public ApiResponse insertAfterSalesInfo(@RequestBody AfterSales afterSales) {
        afterSalesService.insertAfterSalesInfo(afterSales);
        return ApiResponse.success();
    }

    @PostMapping("/updateAfterSalesInfo")
    public ApiResponse updateAfterSalesInfo(@RequestBody AfterSales afterSales) {
        afterSalesService.updateAfterSalesInfo(afterSales);
        return ApiResponse.success();
    }

    @PostMapping("/getAfterSalesList")
    public ApiResponse getAfterSalesList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> afterSalesList = afterSalesService.getAfterSalesList(searchMap);
        return ApiResponse.success(afterSalesList);
    }
}
