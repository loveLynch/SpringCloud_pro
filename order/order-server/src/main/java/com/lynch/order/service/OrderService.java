package com.lynch.order.service;

import com.lynch.order.dto.OrderDTO;

/**
 * Created by lynch on 2020-01-13.
 **/
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单（只能卖家来操作）
     *
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
