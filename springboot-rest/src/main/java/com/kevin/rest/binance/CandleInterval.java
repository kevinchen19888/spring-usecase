package com.kevin.rest.binance;

import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * 标准 K线周期
 */
public enum CandleInterval {
    MINUTES_1(TimeUnit.MINUTES.toMillis(1), "1m"),
    MINUTES_3(TimeUnit.MINUTES.toMillis(3), "3m"),
    MINUTES_5(TimeUnit.MINUTES.toMillis(5), "5m"),
    MINUTES_15(TimeUnit.MINUTES.toMillis(15), "15m"),
    MINUTES_30(TimeUnit.MINUTES.toMillis(30), "30m"),
    HOUR_1(TimeUnit.HOURS.toMillis(1), "1h"),
    HOUR_2(TimeUnit.HOURS.toMillis(2), "2h"),
    HOUR_3(TimeUnit.HOURS.toMillis(3), "3h"),
    HOUR_4(TimeUnit.HOURS.toMillis(4), "4h"),
    HOUR_6(TimeUnit.HOURS.toMillis(6), "6h"),
    HOUR_8(TimeUnit.HOURS.toMillis(8), "8h"),
    HOUR_12(TimeUnit.HOURS.toMillis(12), "12h"),
    DAY_1(TimeUnit.DAYS.toMillis(1), "1d"),
    DAY_3(TimeUnit.DAYS.toMillis(3), "3d"),
    DAY_7(TimeUnit.DAYS.toMillis(7), "7d"),
    WEEK_1(TimeUnit.DAYS.toMillis(7), "1w"),
    DAY_14(TimeUnit.DAYS.toMillis(14), "14d"),
    MONTH_1_M(TimeUnit.DAYS.toMillis(30), "1M"),
    MONTH_1(TimeUnit.DAYS.toMillis(30), "1Mon"),
    MONTH_3(TimeUnit.DAYS.toMillis(90), "3Mon"),
    MONTH_6(TimeUnit.DAYS.toMillis(180), "6Mon"),
    YEAR_1(TimeUnit.DAYS.toMillis(365), "1Y");

    private final long milliseconds;
    private final String symbol;

    CandleInterval(long milliseconds, String symbol) {
        this.milliseconds = milliseconds;
        this.symbol = symbol;
    }

    public long getMilliSeconds() {
        return milliseconds;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CandleInterval fromSymbol(@NonNull String symbol) {
        for (CandleInterval candleInterval : CandleInterval.values()) {
            if (candleInterval.getSymbol().equals(symbol))
                return candleInterval;
        }
        throw new IllegalArgumentException("非法参数: " + symbol);
    }
}
