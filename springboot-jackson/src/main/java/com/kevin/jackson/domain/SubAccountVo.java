package com.kevin.jackson.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 母账户获取子账户的各个账户里的资金余额信息。
 */
@Data
@JsonRootName(value = "data")
public class SubAccountVo {
    @JsonProperty("sub_account")
    private String subAccount;// 账户名
    @JsonProperty("asset_valuation")
    private BigDecimal assetValuation;// 以btc为单位 账户资产总估值

    @JsonProperty("account_type:spot")
    private List<AccountType> accountTypeSpot;
    @JsonProperty("account_type:futures")
    private List<AccountType> accountTypeFutures;
    @JsonProperty("account_type:otc")
    private List<AccountType> accountTypeOtc;
    @JsonProperty("account_type:margin")
    private List<AccountType> accountTypeMargin;
    @JsonProperty("account_type:funding")
    private List<AccountType> accountTypeFunding;
    @JsonProperty("account_type:PiggyBank")
    private List<AccountType> accountTypePiggyBank;
    @JsonProperty("account_type:swap")
    private List<AccountType> accountTypeSwap;
    @JsonProperty("account_type:option")
    private List<AccountType> accountTypeOption;
    @JsonProperty("account_type:Mining account")
    private List<AccountType> accountTypeMiningAccount;

    @Data
    public static class AccountType {
        private BigDecimal balance;// 账户余额 （可用余额和冻结余额的总和）
        private BigDecimal hold;// 冻结（不可用）
        private BigDecimal available;// 可用余额 --币币和资金
        private BigDecimal equity;// 账户权益 --交割和永续
        @JsonProperty("max_withdraw")
        private BigDecimal maxWithdraw;// 可划转数量
        private String currency;// 币种，如BTC

        public void setOthers(String key,String val) {
            this.others.put(key, val);
        }

        @JsonAnySetter
        private Map<String, String> others = new HashMap<>();

    }

}
