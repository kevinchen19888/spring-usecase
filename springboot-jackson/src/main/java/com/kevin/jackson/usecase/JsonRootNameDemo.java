package com.kevin.jackson.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kevin.jackson.domain.SubAccountVo;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
public class JsonRootNameDemo {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);// 序列化时需进行此配置
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);// 进行反序列化时需进行此配置
        mapper.registerModule(new JavaTimeModule());
        String json = "{\"data\":{\"sub_account\":\"Test\",\"asset_valuation\":0.00003463,\"account_type:futures\":[{\"balance\":0.00000245,\"max_withdraw\":0.00000245,\"currency\":\"BTC\",\"underlying\":\"BTC-USD\",\"equity\":0.00000245},{\"balance\":1.053473,\"max_withdraw\":1.053473,\"currency\":\"XRP\",\"underlying\":\"XRP-USD\",\"equity\":1.053473}],\"account_type:spot\":[{\"balance\":0.000312544038152,\"max_withdraw\":0.000312544038152,\"available\":0.000312544038152,\"currency\":\"USDT\",\"hold\":0}]}}";
        SubAccountVo vo = mapper.readValue(json, SubAccountVo.class);

        System.out.println(vo);
        Assertions.assertNotNull(vo);
    }

}
