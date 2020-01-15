package com.lynch.product.VO;

import lombok.Data;

/**
 * Created by lynch on 2020-01-06.
 * http请求返回的最外层对象
 **/
@Data
public class ResultVO<T> {

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体内容
    private T data;
}
