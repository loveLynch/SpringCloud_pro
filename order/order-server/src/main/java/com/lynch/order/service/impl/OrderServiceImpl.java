package com.lynch.order.service.impl;

import com.lynch.order.domain.OrderDetail;
import com.lynch.order.domain.OrderMaster;
import com.lynch.order.dto.OrderDTO;
import com.lynch.order.enums.OrderStatusEnum;
import com.lynch.order.enums.PayStatusEnum;
import com.lynch.order.repository.OrderDetailRepository;
import com.lynch.order.repository.OrderMasterRepository;
import com.lynch.order.service.OrderService;
import com.lynch.order.utils.KeyUtil;
import com.lynch.product.client.ProductClient;
import com.lynch.product.common.DecreaseStockInput;
import com.lynch.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lynch on 2020-01-13.
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();//订单唯一id
        // 1.查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);

        /***
         * 异步扣除库存
         //读redis
         //减库存并将新值重新设置进redis,注意分布式锁
         //入库的事务性,订单入库异常，手动回滚redis
         ***/


        // 2.计算总价（生成订单详情）
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // 3.扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);


        // 4.订单入库（生成订单）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
