package com.kevin.startercreate.processor;

/**
 * @author kevin
 */
public interface FormatProcessor {
    /**
     * 定义一个格式化的方法
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> String format(T obj);
}
