package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IAfterSalesDao;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.service.AfterSalesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AfterSalesServiceImpl implements AfterSalesService {

    @Resource
    private IAfterSalesDao afterSalesDao;

    @Override
    public void insertAfterSalesInfo(AfterSales afterSales) {
        afterSales.setState(Constant.AfterSalesStateEnum.WAITING_AFTER_SALES.getStateCode());
        afterSalesDao.insertAfterSalesInfo(afterSales);
    }

    @Override
    public void updateAfterSalesInfo(AfterSales afterSales) {
        afterSalesDao.updateAfterSalesInfo(afterSales);
    }

    @Override
    public Map<String, Object> getAfterSalesList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<AfterSales> afterSalesList = afterSalesDao.getAfterSalesList();
        PageInfo<AfterSales> pageInfo = new PageInfo<>(afterSalesList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", afterSalesList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

}
