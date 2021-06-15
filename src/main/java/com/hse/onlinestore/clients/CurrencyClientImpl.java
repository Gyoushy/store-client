package com.hse.onlinestore.clients;

import java.math.BigDecimal;

import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.CurrenyClientApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class CurrencyClientImpl implements CurrencyClient {

    private final String url;

    private final String apiKey;

    public CurrencyClientImpl(@Value("${currency.client.url}") String url, @Value("${currency.client.apiKey}") String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override public BigDecimal getPriceInEuro(BigDecimal price, CurrencyDto currency) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_key", apiKey)
                .queryParam("from", currency.name())
                .queryParam("to", CurrencyDto.EUR.name())
                .queryParam("amount", price);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        log.info("currency conversion operation from {} to {} with amount {}", currency.name(), CurrencyDto.EUR.name(), price);
        HttpEntity<CurrenyClientApiResponse> response = new RestTemplate().exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                CurrenyClientApiResponse.class);

        if (response.getBody() == null) {
            log.error("Currency Conversion Response - body is empty");
            return null;
        }

        if (!response.getBody().isSuccess()) {
            log.error("Currency Conversion Response - operation failed");
            return null;
        }

        return new BigDecimal(response.getBody().getResult());
    }
}
