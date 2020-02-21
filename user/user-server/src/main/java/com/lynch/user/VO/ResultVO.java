package com.lynch.user.VO;

import lombok.Data;

/**
 * Created by lynch on 2020-02-21.
 **/
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}