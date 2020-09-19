package com.kevin.jackson.util;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 自定义Module:
 * 1,继承SimpleModule
 * 2,无此构造中调用父类无参构造,并调用 addSerializer方法或 addDeserializer方法指定序列化或反序列化规则
 *
 * @author kevin
 */
public class BaseModule extends SimpleModule {

    public BaseModule() {
        super();
        addSerializer(Boolean.class, new OptimizedBooleanSerializer());
    }
}
