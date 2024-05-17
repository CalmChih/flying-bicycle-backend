package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IAfterSalesDao;
import top.fallingintodreams.flying.bicycle.backend.dao.IOrderDao;
import top.fallingintodreams.flying.bicycle.backend.dto.AfterSalesDTO;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.BusinessException;
import top.fallingintodreams.flying.bicycle.backend.po.AfterSales;
import top.fallingintodreams.flying.bicycle.backend.po.Order;
import top.fallingintodreams.flying.bicycle.backend.service.AfterSalesService;
import top.fallingintodreams.flying.bicycle.backend.util.DateConvertUtils;
import top.fallingintodreams.flying.bicycle.backend.vo.AfterSalesVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AfterSalesServiceImpl implements AfterSalesService {

    @Resource
    private IAfterSalesDao afterSalesDao;
    @Resource
    private IOrderDao orderDao;

    @Resource
    private AlipayConfig alipayConfig;

    private static final Integer RETRY_TIMES = 3;

    @Override
    @Transactional
    public void applyForAfterSales(AfterSalesDTO afterSalesDTO) {
        AfterSales afterSales = Dto2Po(afterSalesDTO);
        afterSalesDao.insertAfterSalesInfo(afterSales);
        updateOrderState(afterSales.getOrderId(), Constant.OrderStateEnum.APPLY_FOR_AFTER_SALES);
    }

    private void updateOrderState(String orderId, Constant.OrderStateEnum orderStateEnum) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(orderStateEnum.getStateCode());
        orderDao.updateOrder(order);
    }

    @NotNull
    private static AfterSales Dto2Po(AfterSalesDTO afterSalesDTO) {
        AfterSales afterSales = new AfterSales();
        afterSales.setOrderId(afterSalesDTO.getOrderId());
        afterSales.setUsername(afterSalesDTO.getUsername());
        afterSales.setGoodsId(afterSalesDTO.getGoodsId());
        afterSales.setRequestRefundAmount(afterSalesDTO.getRequestRefundAmount());
        afterSales.setRefundReason(afterSalesDTO.getRefundReason());
        afterSales.setState(Constant.AfterSalesStateEnum.WAITING_AFTER_SALES.getStateCode());
        return afterSales;
    }

    @Override
    @Transactional
    public void updateAfterSalesInfo(AfterSales afterSales) throws AlipayApiException {
        if (Objects.isNull(afterSales.getActualRefundAmount())) {
            // 驳回售后申请
            afterSales.setState(Constant.AfterSalesStateEnum.REJECTED.getStateCode());
            updateOrderState(afterSales.getOrderId(), Constant.OrderStateEnum.VEHICLES_RETURNED);
        } else {
            // 处理售后
            boolean isRefunded = false;
            for (int i = 0; i < RETRY_TIMES; i++) {
                isRefunded = alipayRefund(afterSales.getOrderId(), afterSales.getActualRefundAmount().toString());
                if (isRefunded) {
                    break;
                }
            }
            if (!isRefunded) {
                throw new BusinessException("退款失败");
            }
            afterSales.setState(Constant.AfterSalesStateEnum.AFTER_SALES_SUCCESS.getStateCode());
            updateOrderState(afterSales.getOrderId(), Constant.OrderStateEnum.REFUNDED);
        }
        afterSalesDao.updateAfterSalesInfo(afterSales);
    }

    private boolean alipayRefund(String orderId, String refundAmount) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(orderId);
        model.setRefundAmount(refundAmount);
        model.setOutRequestNo(orderId);
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String fundChange = response.getFundChange();
            if (StringUtils.isNotBlank(fundChange)) {
                if ("Y".equals(fundChange)) {
                    return true;
                } else {
                    return alipayQueryRefund(orderId);
                }
            }
        }
        return false;
    }

    @NotNull
    private AlipayClient getAlipayClient() throws AlipayApiException {
        return new DefaultAlipayClient(alipayConfig);
    }

    private boolean alipayQueryRefund(String orderId) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(orderId);
        model.setOutRequestNo(orderId);
        model.setOutRequestNo(orderId);
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String refundStatus = response.getRefundStatus();
            if (StringUtils.isNotBlank(refundStatus)) {
                return "REFUND_SUCCESS".equals(refundStatus);
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAfterSalesList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<AfterSalesVO> afterSalesList = afterSalesDao.getAfterSalesList();
        List<AfterSalesDTO> afterSalesDTOList = afterSalesList.stream().map(this::vo2Dto).collect(Collectors.toList());
        PageInfo<AfterSalesVO> pageInfo = new PageInfo<>(afterSalesList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", afterSalesDTOList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

    private AfterSalesDTO vo2Dto(AfterSalesVO afterSalesVO) {
        if (Objects.isNull(afterSalesVO)) {
            return null;
        }
        AfterSalesDTO afterSalesDTO = new AfterSalesDTO();
        afterSalesDTO.setOrderId(afterSalesVO.getOrderId());
        afterSalesDTO.setUsername(afterSalesVO.getUsername());
        afterSalesDTO.setGoodsId(afterSalesVO.getGoodsId());
        afterSalesDTO.setGoodsTitle(afterSalesVO.getGoodsTitle());
        afterSalesDTO.setGoodsImg(afterSalesVO.getGoodsImg());
        afterSalesDTO.setGoodsDesc(afterSalesVO.getGoodsDesc());
        afterSalesDTO.setGoodsPrice(afterSalesVO.getGoodsPrice());
        afterSalesDTO.setTotalPrice(afterSalesVO.getTotalPrice());
        afterSalesDTO.setRequestRefundAmount(afterSalesVO.getRequestRefundAmount());
        afterSalesDTO.setRefundReason(afterSalesVO.getRefundReason());
        afterSalesDTO.setActualRefundAmount(afterSalesVO.getActualRefundAmount());
        afterSalesDTO.setSellerReply(afterSalesVO.getSellerReply());
        afterSalesDTO.setState(Constant.AfterSalesStateEnum.convert(afterSalesVO.getState()));
        afterSalesDTO.setPayTime(DateConvertUtils.dateFormatToString(afterSalesVO.getPayTime()));
        afterSalesDTO.setReservationStartTime(DateConvertUtils.dateFormatToString(afterSalesVO.getReservationStartTime()));
        afterSalesDTO.setReservationEndTime(DateConvertUtils.dateFormatToString(afterSalesVO.getReservationEndTime()));
        afterSalesDTO.setCreateTime(DateConvertUtils.dateFormatToString(afterSalesVO.getCreateTime()));
        return afterSalesDTO;
    }

    @Override
    public AfterSalesDTO getAfterSalesByOrderId(String orderId) {
        AfterSalesVO afterSalesVO = afterSalesDao.getAfterSalesByOrderId(orderId);
        return vo2Dto(afterSalesVO);
    }
}
