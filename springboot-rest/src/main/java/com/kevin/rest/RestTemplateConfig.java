package com.kevin.rest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: kevin
 */
@Configuration
@ConfigurationProperties(
        prefix = "rest.template"
)
public class RestTemplateConfig {

    private int maxTotalConnection = 200; // 连接池的最大连接数
    private int maxConnectionPerRoute = 200; // 同路由的并发数
    private int connectionTimeout = 5 * 1000; // 连接超时(连接上服务器(握手成功)的超时时间)
    private int socketTimeout = 10000; // 服务器返回数据(response)的超时时间
    private int connectionRequestTimeout = 5000; // 从连接池中获取连接的超时时间
    private String charset = "UTF-8";

    //@Bean
    //public RestTemplate restTemplate() {
    //    RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
    //    // 我们采用RestTemplate内部的MessageConverter
    //    // 重新设置StringHttpMessageConverter字符集，解决中文乱码问题
    //    modifyDefaultCharset(restTemplate);
    //    return restTemplate;
    //}

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(maxTotalConnection);
        // 路由是对maxTotal的细分,连接池中相同目标IP和端口号的HTTP连接数量最大数
        connectionManager.setDefaultMaxPerRoute(maxConnectionPerRoute);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

    //private void modifyDefaultCharset(RestTemplate restTemplate) {
    //    List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
    //    HttpMessageConverter<?> converterTarget = null;
    //    for (HttpMessageConverter<?> item : converterList) {
    //        if (StringHttpMessageConverter.class == item.getClass()) {
    //            converterTarget = item;
    //            break;
    //        }
    //    }
    //    if (null != converterTarget) {
    //        converterList.remove(converterTarget);
    //    }
    //    Charset defaultCharset = Charset.forName(charset);
    //    converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    //}

}
