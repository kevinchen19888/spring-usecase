import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kevin.rest.huobi.AccountAssetResp;
import com.kevin.rest.huobi.HuobiApiSignature;
import com.kevin.rest.huobi.UrlParamsBuilder;
import com.kevin.rest.util.DateUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */

@Slf4j
public class HuobiApiTest {
    private RestTemplate restTemplate;
    private String apiKey;
    private String secretKey;
    private final String url = "https://api.huobi.pro";
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";


    @Before
    public void init() {
        restTemplate = new RestTemplate();
        apiKey = "04b91620-1b5a9dbd-84ca7db6-b1rkuf4drg";
        secretKey = "";
    }

    @Test
    public void testAccountInfo() throws MalformedURLException, InterruptedException {
        //UrlParamsBuilder builder = UrlParamsBuilder.build();
        //String host = new URL(url).getHost();
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type", "application/x-www-form-urlencoded");
        //headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        //HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        //
        //String signUrl = HuobiApiSignature.createSignature(apiKey, secretKey, "GET", host, "/v1/account/accounts", builder).buildUrl();
        //String url = this.url + "/v1/account/accounts" + signUrl;
        //URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();
        //ResponseEntity<String> entity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        //System.out.println(entity);

        String[] split = "market.btcusdt.kline.1min".split("kline.");

        String s = "{\"id\":1596084900}";
        JSONObject jsonObject = JSON.parseObject(s);
        LocalDateTime dateTime = DateUtils.getEpochMilliByTime(jsonObject.getLong("id")*1000);
        System.out.println(dateTime);

        //System.out.println(split[1]);;
    }

    @Test
    public void testDeposit_withdraw() throws MalformedURLException {
        String host = new URL(url).getHost();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("user-agent", USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("type", "deposit")
                .putToUrl("direct", "next");
        String signUrl = HuobiApiSignature.createSignature(apiKey, secretKey, "GET", host, "/v1/query/deposit-withdraw", builder).buildUrl();
        String url = this.url + "/v1/query/deposit-withdraw" + signUrl;
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();
        ResponseEntity<String> entity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        System.out.println(entity.getBody());

    }

    @Test
    public void testPlaceOrder() throws MalformedURLException {
        String host = new URL(url).getHost();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("user-agent", USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToPost("account-id", 14663304)
                .putToPost("amount", "1.0")
                .putToPost("price", "1.0")
                .putToPost("symbol", "htbtc")
                .putToPost("type", "sell-limit")
                .putToPost("client-order-id", System.currentTimeMillis())
                .putToPost("source", "spot-api");

        String signUrl = HuobiApiSignature.createSignature(apiKey, secretKey, "post", host, "/v1/order/orders/place", builder).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(this.url + "/v1/order/orders/place" + signUrl).build(true).toUri();

        ResponseEntity<String> entity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        System.out.println(entity.getBody());
    }

    @SneakyThrows
    @Test
    public void testAsset_transfer() {

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("account-id", 14663304)
                .putToUrl("transact-types", "transfer")
                .putToUrl("sort", "desc");
        builder.putToUrl("start-time", LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.of("+8")).toEpochMilli());

        String signedParams = HuobiApiSignature.createSignature(apiKey, secretKey,
                "GET", new URL(url).getHost(), "/v1/account/history", builder).buildUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("user-agent", USER_AGENT);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(url + "/v1/account/history" + signedParams).build(true).toUri();

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        System.out.println(exchangeResp.getBody());
    }

    @SneakyThrows
    @Test
    public void testHistory_orders() {
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol","btcusdt")
                .putToUrl("states","filled,canceled")
                .putToUrl("direct", "prev")
                .putToUrl("from",10);

        String signedParams = HuobiApiSignature.createSignature(apiKey, secretKey,
                "GET", new URL(url).getHost(), "/v1/order/orders", builder).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(url + "/v1/order/orders" + signedParams).build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", USER_AGENT);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        System.out.println(exchangeResp.getBody());
    }

}
