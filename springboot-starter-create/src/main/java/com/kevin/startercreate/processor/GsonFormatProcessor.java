package com.kevin.startercreate.processor;

import com.google.gson.Gson;

/**
 * 基于Gson进行的json格式化
 */
public class GsonFormatProcessor implements FormatProcessor {

    @Override
    public <T> String format(T obj) {
        Gson gson = new Gson();
        return "GsonFormatProcessor:"+gson.toJson(obj);
    }
}
