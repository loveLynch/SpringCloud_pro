package com.lynch.product.utils;

import com.lynch.product.VO.ResultVO;

/**
 * Created by lynch on 2020-01-06.
 **/
public class ResultUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
}
