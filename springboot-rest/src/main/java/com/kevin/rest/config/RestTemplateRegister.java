package com.kevin.rest.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 此register会注册多个RestTemplate 实例,供不同服务的api接口进行调用
 *
 * @author kevin chen
 */
@Component
@Slf4j
public class RestTemplateRegister {
    private Environment environment;
    private DefaultListableBeanFactory defaultListableBeanFactory;

    private final String prefix = "rest.template.";

    private String charset = "UTF-8";

    @Autowired
    public RestTemplateRegister(Environment environment, DefaultListableBeanFactory defaultListableBeanFactory) {
        this.environment = environment;
        this.defaultListableBeanFactory = defaultListableBeanFactory;
        init();
    }

    private void init() {
        String exchanges = environment.getProperty("rest.template.exchanges");
        String[] exchangeArr = exchanges.split(",");
        if (exchangeArr != null) {
            for (String exchange : exchangeArr) {
                // 设置整个连接池最大连接数 根据自己的场景决定
                String maxTotalConnection = environment.getProperty(prefix + exchange + "maxTotalConnection");
                String maxConnectionPerRoute = environment.getProperty(prefix + exchange + "maxConnectionPerRoute");
                String connectionTimeout = environment.getProperty(prefix + exchange + "connectionTimeout");
                String socketTimeout = environment.getProperty(prefix + exchange + "socketTimeout");
                String connectionRequestTimeout = environment.getProperty(prefix + exchange + "connectionRequestTimeout");

                Registry<ConnectionSocketFactory> restRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build();
                PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(restRegistry);
                connectionManager.setMaxTotal(maxTotalConnection != null ? Integer.parseInt(maxTotalConnection) : 100);
                // 路由是对maxTotal的细分,连接池中相同目标IP和端口号的HTTP连接数量最大数
                connectionManager.setDefaultMaxPerRoute(maxConnectionPerRoute != null ? Integer.parseInt(maxConnectionPerRoute) : 100);
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(socketTimeout != null ? Integer.parseInt(socketTimeout) : 10000)
                        .setConnectTimeout(connectionTimeout != null ? Integer.parseInt(connectionTimeout) : 5000)
                        .setConnectionRequestTimeout(connectionRequestTimeout != null ? Integer.parseInt(connectionRequestTimeout) : 5000)
                        .build();

                CloseableHttpClient httpClient = HttpClientBuilder.create()
                        .setDefaultRequestConfig(requestConfig)
                        .setConnectionManager(connectionManager)
                        .build();

                HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
                RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
                // 重新设置StringHttpMessageConverter字符集，解决中文乱码问题
                modifyDefaultCharset(restTemplate);
                // 将实例化后的bean注入到spring容器中
                defaultListableBeanFactory.registerSingleton(exchange + "RestTemplate", restTemplate);
                System.out.println(defaultListableBeanFactory.getBean(exchange + "RestTemplate")); // todo
            }
        }
    }

    private void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        Charset defaultCharset = Charset.forName(charset);
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }


    public static void main(String[] args) {

    }
}
