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
}
