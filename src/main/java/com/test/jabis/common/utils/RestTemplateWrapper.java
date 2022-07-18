package com.test.jabis.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collections;


@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateWrapper {
    private final RestTemplate restTemplate;

    public HttpHeaders getDefaultApiHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public <T> HttpEntity<T> createJsonEntity(T body) {
        final HttpHeaders headers = getDefaultApiHeaders();
        return new HttpEntity<>(body, headers);
    }
    public <T> HttpEntity<T> createJsonEntity(T body, HttpHeaders headers) {
        return new HttpEntity<>(body, headers);
    }

    public <T> ResponseEntity<T> callApi(String url, HttpMethod method, HttpEntity<?> entity,
                                         ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        try {
            final URI uriObject = getUriObject(url, uriVariables);
            ResponseEntity<T> result =  restTemplate.exchange(uriObject, method, entity, responseType);
            return result;
        } catch (HttpClientErrorException ex) {
            handleClientException(url, ex);
        } catch (Exception ex) {
            handleRestClientException(url, ex);
        }

        return null;
    }


    private URI getUriObject(String url, Object... uriVariables) throws URISyntaxException {
        try {
            if (uriVariables == null) {
                return new URI(url);
            } else {
                final UriTemplateHandler templateHandler = restTemplate.getUriTemplateHandler();
                return templateHandler.expand(url, uriVariables);
            }
        } catch (URISyntaxException ex) {
            log.error("Unexpected exception:", ex);
            throw ex;
        }
    }

    private void handleClientException(String url, HttpClientErrorException ex) {
        log.info(MessageFormat.format("HttpClientErrorException Request {0}. (message >>> {1})", url, ex.getMessage()),
                ex);
        throw ex;
    }

    private void handleRestClientException(String url, Exception ex) {
        log.error(url, ex);
    }
}
