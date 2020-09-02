package com.kevin.rest.huobi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountAssetResp {
    /**
     * 币种
     */
    private String coin;
    /**
     * 可用余额
     */
    private BigDecimal balance;
    /**
     * 冻结余额
     */
    private BigDecimal frozen;

}
