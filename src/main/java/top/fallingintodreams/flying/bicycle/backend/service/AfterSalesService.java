package top.fallingintodreams.flying.bicycle.backend.service;

import com.alipay.api.AlipayApiException;
import top.fallingintodreams.flying.bicycle.backend.dto.AfterSalesDTO;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;

import java.util.Map;

public interface AfterSalesService {

    void applyForAfterSales(AfterSalesDTO afterSalesDTO);

    void updateAfterSalesInfo(AfterSales afterSales) throws AlipayApiException;

    Map<String, Object> getAfterSalesList(Map<String, Object> searchMap);

    AfterSalesDTO getAfterSalesByOrderId(String orderId);

}
