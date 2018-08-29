package com.bcfou.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * @Author: 编程否
 * @Date: 2018/8/26 19:09
 */
public class ResultVO extends HashMap<String, Object> {
    private static final long serialVersionUID = 1425594067026111525L;

    private Integer code;
    private String message;


    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;

    }
    public ResultVO(){
        this.code = 0;
        this.message = "seccuss";

    }
    public static ResultVO ok(Map<String, Object> map){
        ResultVO resultVO = new ResultVO();
        resultVO.putAll(map);
        return resultVO;
    }
    public static ResultVO ok(){
        return new ResultVO();
    }
    public static ResultVO error(Integer code, String message){
        return new ResultVO(code, message);
    }
}
