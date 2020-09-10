import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kevin.rest.binance.CandleTick;
import com.kevin.rest.binance.CollectionUtil;
import com.kevin.rest.binance.HmacSHA256Signer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author kevin chen
 */
public class BinanceApiTest {
    private HttpEntity<MultiValueMap<String, Object>> httpEntity;
    private final String binanceUrl = "https://api.binance.com";
    private final String secret = "";

    @Before
    public void setHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.add("X-MBX-APIKEY", "abbCPrrkfynqaEWN7UVnJxCNRHQPigU49i90WjHDCjzHmXdnR5DOuZoLlBGSEihG");
        httpEntity = new HttpEntity<>(headers);
    }

    @Test
    public void test_findHasUserChargeWithdrawRecord() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        long timeStamp = System.currentTimeMillis();
        long startTimeStamp = LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        params.put("timestamp", timeStamp);
        params.put("startTime", startTimeStamp);
        String signature = HmacSHA256Signer.sign(secret, params);

        ResponseEntity<String> resp = restTemplate.exchange(binanceUrl
                        + "/sapi/v1/capital/deposit/hisrec" + "?startTime=" + startTimeStamp + "&timestamp=" + timeStamp + "&signature=" + signature,
                HttpMethod.GET, httpEntity, String.class);
        System.out.println(resp.getBody());
    }

    @Test
    public void test_findUserStatus() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new LinkedHashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("timestamp", timeStamp);
        String signature = HmacSHA256Signer.sign(secret, params);
        params.put("signature", signature);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.add("X-MBX-APIKEY", "abbCPrrkfynqaEWN7UVnJxCNRHQPigU49i90WjHDCjzHmXdnR5DOuZoLlBGSEihG");
        MultiValueMap multiValueMap = CollectionUtil.toMultiValueMap(params);
        //httpEntity = new HttpEntity<>(null,headers);

        System.out.println("开始请求-----------");
        //ResponseEntity<String> response = restTemplate.exchange("https://api.binance.com" + "/wapi/v3/accountStatus.html?" + "timestamp=" + timeStamp + "&signature=" + signature,
        //        HttpMethod.GET, httpEntity, String.class);
        ResponseEntity response = restTemplate.exchange("https://api.binance.com" + "/wapi/v3/accountStatus.html",
                HttpMethod.GET, new HttpEntity<>(headers), String.class, multiValueMap);

        System.out.println(response.getBody());
    }

    @Test
    public void testCreateOrder() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new LinkedHashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("symbol", "LINKETH");
        params.put("side", "BUY");
        params.put("type", "LIMIT");
        params.put("price", "0.029");
        params.put("quantity", "1000");
        params.put("recvWindow", "20000");
        params.put("timeInForce", "GTC");
        params.put("timestamp", timeStamp);
        // todo 签名
        String signature = HmacSHA256Signer.sign(secret, params);
        params.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", "abbCPrrkfynqaEWN7UVnJxCNRHQPigU49i90WjHDCjzHmXdnR5DOuZoLlBGSEihG");

        String request = HmacSHA256Signer.createSigns(params);
        HttpEntity<String> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> exchangeResp = restTemplate.postForEntity(binanceUrl + "/api/v3/order/test", httpEntity, String.class);
        System.out.println(exchangeResp.getBody());
    }

    @Test
    public void test_supportedCoinPairs() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity(binanceUrl + "/api/v3/exchangeInfo", String.class);
        JSONObject jsonObject = JSON.parseObject(resp.getBody());
        JSONArray symbols = jsonObject.getJSONArray("symbols");

        Set<String> symbolSet = new HashSet<>(1024);
        for (int i = 0; i < symbols.size(); i++) {
            JSONObject symbol = symbols.getJSONObject(i);
            if ("TRADING".equalsIgnoreCase(symbol.getString("status"))) {
                symbolSet.add(symbol.getString("baseAsset"));
            }
        }
        System.out.println(symbolSet);
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test_kline() {
        RestTemplate restTemplate = new RestTemplate();
        long startTimeStamp = LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        ResponseEntity<String> resp = restTemplate.getForEntity(binanceUrl + "/api/v3/klines" + "?symbol=BTCUSDT&interval=1m&startTime=" + startTimeStamp
                        + "&limit=1000"
                , String.class);
        JSONArray jsonArray = JSONArray.parseArray(resp.getBody());
        List<CandleTick> klines = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            CandleTick candleTick = new CandleTick();
            JSONArray kline = jsonArray.getJSONArray(i);

            candleTick.setTimeStamp(LocalDateTime.ofEpochSecond(kline.getLongValue(0) / 1000, 0, ZoneOffset.ofHours(8)));
            candleTick.setOpen(kline.getBigDecimal(1));
            candleTick.setHigh(kline.getBigDecimal(2));
            candleTick.setLow(kline.getBigDecimal(3));
            candleTick.setClose(kline.getBigDecimal(4));
            candleTick.setVolume(kline.getBigDecimal(5));

            Map<String, String> reserveMap = new HashMap<>();
            LocalDateTime closeTime = LocalDateTime.ofEpochSecond(kline.getLongValue(6) / 1000, 0, ZoneOffset.ofHours(8));
            reserveMap.put("close_time", TIME_FORMATTER.format(closeTime));
            reserveMap.put("count", kline.getString(8));
            reserveMap.put("bid_volume", kline.getString(9));
            reserveMap.put("bid_amount", kline.getString(10));
            candleTick.setReserveMap(reserveMap);
            klines.add(candleTick);
        }
        System.out.println(klines);

    }

    public static void main(String[] args) {
        BigDecimal filledFee = new BigDecimal("-0.2");
        BigDecimal n = null;
        if (true) {
            BigDecimal bigDecimal = new BigDecimal("-0.2");
            n = filledFee.add(bigDecimal);

        }
        System.out.println(n);
    }


}
