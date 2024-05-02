package top.fallingintodreams.flying.bicycle.backend.controller;

import com.alipay.api.AlipayApiException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.AfterSalesDTO;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.service.AfterSalesService;

import java.util.Map;

@RestController
@RequestMapping("/afterSales")
public class AfterSalesController {

    @Resource
    private AfterSalesService afterSalesService;

    @PostMapping("/applyForAfterSales")
    public ApiResponse applyForAfterSales(@RequestBody AfterSalesDTO afterSalesDTO) {
        afterSalesService.applyForAfterSales(afterSalesDTO);
        return ApiResponse.success();
    }

    @PostMapping("/updateAfterSalesInfo")
    public ApiResponse updateAfterSalesInfo(@RequestBody AfterSales afterSales) throws AlipayApiException {
        afterSalesService.updateAfterSalesInfo(afterSales);
        return ApiResponse.success();
    }

    @PostMapping("/getAfterSalesList")
    public ApiResponse getAfterSalesList(@RequestBody Map<String, Object> searchMap) {
        Map<String, Object> afterSalesList = afterSalesService.getAfterSalesList(searchMap);
        return ApiResponse.success(afterSalesList);
    }

    @GetMapping("/getAfterSalesByOrderId")
    public ApiResponse getAfterSalesByOrderId(@RequestParam String orderId) {
        AfterSalesDTO afterSalesDTO = afterSalesService.getAfterSalesByOrderId(orderId);
        return ApiResponse.success(afterSalesDTO);
    }
}
