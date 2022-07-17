package com.test.jabis.tax.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.jabis.common.utils.RestTemplateWrapper;
import com.test.jabis.tax.dao.TaxInfo;
import com.test.jabis.tax.dao.TaxRepository;
import com.test.jabis.tax.dto.RefundResponse;
import com.test.jabis.tax.dto.ScrapData;
import com.test.jabis.tax.dto.ScrapResponse;
import com.test.jabis.user.dao.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService{
    private final  RestTemplateWrapper restTemplateWrapper;
    private final ObjectMapper objectMapper;
    private final TaxRepository taxRepository;

    @Value("${apis.scrap-api}")
    private String scrapApi;

    @Override
    public ScrapResponse getUserScrap(User user, String token) {
        // todo Spring security
        return callScrapApi(user, token);
    }

    @Override
    public RefundResponse getRefund(User user, String token) {
        TaxInfo taxInfo = taxRepository.findTopByUserId(user.getUserId())
                .orElseGet(() -> {
                    JSONParser jsonParser = new JSONParser();
                    Integer income = null;
                    Integer tax = null;
                    ScrapResponse scrapResponse = getUserScrap(user, token);
                    JSONObject jsonObject = null;
                    try {
                        ScrapData scrapData = objectMapper.readValue(
                                objectMapper.writeValueAsString(scrapResponse.getData()), ScrapData.class);
                        String jsonList = objectMapper.writeValueAsString(scrapData.getJsonList());
                        jsonObject = (JSONObject) jsonParser.parse(jsonList);
                        ArrayList<String> keySets = new ArrayList<>(jsonObject.keySet());
                        for (String key : keySets) {
                            if (jsonObject.get(key) instanceof JSONArray) {
                                JSONArray jsonArray = (JSONArray) jsonObject.get(key);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject jo = (JSONObject) jsonArray.get(i);
                                    Object in = jo.get("총지급액");
                                    ;
                                    Object max = jo.get("총사용금액");
                                    if (!Objects.isNull(in)) {
                                        income = getOnlyInteger(String.valueOf(in));
                                    }
                                    if (!Objects.isNull(max)) {
                                        tax = getOnlyInteger(String.valueOf(max));
                                    }
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return taxRepository.save(TaxInfo.create(income, tax, user.getUserId()));
                });
        RefundTaxCalculator calculator = new RefundTaxCalculator(taxInfo.getIncome(), taxInfo.getOutputTax());
        RefundResponse refundResponse = RefundResponse.builder()
                .name(user.getName())
                .max(calculator.createMax().toString())
                .sale(calculator.createSale().toString())
                .refund(calculator.createRefund().toString())
                .build();

        return refundResponse;
    }

    private Integer getOnlyInteger(String str) {
        if (Strings.isEmpty(str)) {
            return null;
        }
        return Integer.valueOf(str.replaceAll("[^0-9]", ""));
    }

    private ScrapResponse callScrapApi(User user, String token) {
        Map<String, String> request = new HashMap<>();
        request.put("name", user.getName());
        request.put("regNo", user.getRegNo());
        HttpHeaders headers = restTemplateWrapper.getDefaultApiHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity entity = restTemplateWrapper.createJsonEntity(request, headers);

        ResponseEntity<ScrapResponse> result = restTemplateWrapper.callApi(scrapApi, org.springframework.http.HttpMethod.POST, entity,
                new ParameterizedTypeReference<>() {
                });
        if (result == null || result.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException();
        }
        ScrapResponse scrapResponse = result.getBody();
        return scrapResponse;
    }
}
