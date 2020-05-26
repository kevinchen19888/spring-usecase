package com.kevin.startercreate.processor;

import com.alibaba.fastjson.JSONObject;

public class FastJsonFormatProcessor implements FormatProcessor {
    @Override
    public <T> String format(T obj) {
        return "FastJsonFormatProcessor:"+ JSONObject.toJSONString(obj);
    }
}
