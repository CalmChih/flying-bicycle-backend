package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;

import java.util.Map;

public interface AfterSalesService {

    void insertAfterSalesInfo(AfterSales afterSales);

    void updateAfterSalesInfo(AfterSales afterSales);

    Map<String, Object> getAfterSalesList(Map<String, Object> searchMap);

}
