package com.lynch.order.VO;

import lombok.Data;

/**
 * Created by lynch on 2020-01-14.
 **/
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
