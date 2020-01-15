package com.lynch.order.repository;

import com.lynch.order.OrderApplicationTests;
import com.lynch.order.domain.OrderMaster;
import com.lynch.order.enums.OrderStatusEnum;
import com.lynch.order.enums.PayStatusEnum;
import com.lynch.order.repository.OrderMasterRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Created by lynch on 2020-01-12.
 **/
@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345678");
        orderMaster.setBuyerName("买家");
        orderMaster.setBuyerPhone("17396236629");
        orderMaster.setBuyerAddress("电子科大");
        orderMaster.setBuyerOpenid("111");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster result = orderMasterRepository.save(orderMaster);

        Assert.assertTrue(result != null);
    }


}