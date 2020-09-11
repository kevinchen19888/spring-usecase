package com.kevin.base;

import lombok.SneakyThrows;

import java.io.InputStream;

/**
 * @author kevin
 */
public class ResourceUtil {

    /**
     * @param path resource 目录下文件的路径
     * @return 解析文件后生成的输入流
     */
    @SneakyThrows
    public static InputStream getResourceAsStream(String path) {
        assert path != null;
        return ResourceUtil.class.getClassLoader().getResourceAsStream(path);
    }
}
