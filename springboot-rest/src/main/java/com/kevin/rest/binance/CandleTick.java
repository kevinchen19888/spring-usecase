package com.kevin.rest.binance;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CandleTick {

    /**
     * 开始时间
     */
    LocalDateTime timeStamp;
    /**
     * 最高价
     */
    BigDecimal high;
    /**
     * 开盘价
     */
    BigDecimal open;
    /**
     * 最低价
     */
    BigDecimal low;
    /**
     * 收盘价
     */
    BigDecimal close;
    /**
     * 交易量
     */
    BigDecimal volume;
    /**
     * 备用字段，各交易所特色字段存放；key为字段名，value为值
     */
    Map<String, String> reserveMap;
}
