package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.vo.AfterSalesVO;

import java.util.List;

/**
 * 售后Dao
 */
@Mapper
public interface IAfterSalesDao {

    /**
     * 新增售后信息
     * @param afterSales 售后信息
     */
    void insertAfterSalesInfo(AfterSales afterSales);

    /**
     * 更新售后信息
     * @param afterSales 售后信息
     */
    void updateAfterSalesInfo(AfterSales afterSales);


    List<AfterSalesVO> getAfterSalesList();

    AfterSalesVO getAfterSalesByOrderId(String orderId);

}
