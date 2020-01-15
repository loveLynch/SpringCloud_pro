package com.lynch.order.repository;

import com.lynch.order.domain.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lynch on 2020-01-12.
 **/
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
