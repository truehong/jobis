package com.test.jabis.tax.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.jabis.common.dto.ScrapResponse;
import com.test.jabis.common.utils.RestTemplateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService{
    private final  RestTemplateWrapper restTemplateWrapper;
    private final ObjectMapper objectMapper;

    @Value("${apis.scrap-api}")
    private String scrapApi;

    @Override
    public ScrapResponse getUserScrap() {
        // todo Spring security
        return callScrapApi();
    }

    @Override
    public RefundResponse getRefund() {
        ScrapResponse response =  getUserScrap();
        try{
            String result = objectMapper.writeValueAsString(response);
            JSONObject jsonObject = new JSONObject(result);
            jsonObject.get("");
        }catch (JsonProcessingException ex ){

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RefundTaxCalculator calculator =new RefundTaxCalculator(0);
        RefundResponse refundResponse = RefundResponse.builder()
                .max(calculator.createMax().toString())
                .sale(calculator.createSale().toString())
                .refund(calculator.createRefund().toString())
                .build();

        return refundResponse;
    }

    private ScrapResponse callScrapApi(){
        Map<String, String> request = new HashMap<>();
        request.put("name", "홍길동");
        request.put("regNo", "860824-1655068");
        HttpHeaders headers = restTemplateWrapper.getDefaultApiHeaders();
        headers.set("Authorization", "Bearer " + "token");
        HttpEntity entity = restTemplateWrapper.createJsonEntity(request, headers);
        ResponseEntity<ScrapResponse<Object>> result =  restTemplateWrapper.callApi(scrapApi, org.springframework.http.HttpMethod.POST, entity,
                new ParameterizedTypeReference<>() {
                });
        ScrapResponse scrapResponse = result.getBody();
        return scrapResponse;
    }
}
