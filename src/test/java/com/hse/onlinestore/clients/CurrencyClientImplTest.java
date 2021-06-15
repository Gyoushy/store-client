package com.hse.onlinestore.clients;

import java.math.BigDecimal;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.hse.onlinestore.dtos.CurrencyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWireMock(port = 2404)
class CurrencyClientImplTest {

    @Autowired
    private WireMockServer wirekMockServer;

    private static final String API_KEY = "API_KEY";

    private static final String FROM_CURRENCY = "USD";

    private static final String TO_CURRENCY = "EUR";

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(50);

    private static final BigDecimal FAIL_AMOUNT = BigDecimal.valueOf(60);

    @Test
    void CurrencyClientSuccessCallTest() {
        wirekMockServer.stubFor(
                WireMock.get(urlPathMatching("/api/convert"))
                        .withQueryParam("access_key", WireMock.equalTo(API_KEY))
                        .withQueryParam("from", WireMock.equalTo(FROM_CURRENCY))
                        .withQueryParam("to", WireMock.equalTo(TO_CURRENCY))
                        .withQueryParam("amount", WireMock.equalTo(AMOUNT.toPlainString()))
                        .willReturn(aResponse()
                                            .withStatus(HttpStatus.OK.value())
                                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                            .withBodyFile("CurrencyClientSuccessfulResponse.json")));

        String url = "http://127.0.0.1:2404/api/convert";
        String apiKey = "dd834009acad8013d5427dc9356227d4";
        CurrencyClientImpl client = new CurrencyClientImpl(url, API_KEY);
        BigDecimal result = client.getPriceInEuro(BigDecimal.valueOf(50), CurrencyDto.USD);
        assertNotNull(result);
        assertEquals("3724.305775", result.toString());
    }

    @Test
    void CurrencyClientFailedCallTest() {
        wirekMockServer.stubFor(
                WireMock.get(urlPathMatching("/api/convert"))
                        .withQueryParam("access_key", WireMock.equalTo(API_KEY))
                        .withQueryParam("from", WireMock.equalTo(FROM_CURRENCY))
                        .withQueryParam("to", WireMock.equalTo(TO_CURRENCY))
                        .withQueryParam("amount", WireMock.equalTo(FAIL_AMOUNT.toPlainString()))
                        .willReturn(aResponse()
                                            .withStatus(HttpStatus.OK.value())
                                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        String url = "http://127.0.0.1:2404/api/convert";
        String apiKey = "dd834009acad8013d5427dc9356227d4";
        CurrencyClientImpl client = new CurrencyClientImpl(url, API_KEY);
        BigDecimal result = client.getPriceInEuro(BigDecimal.valueOf(60), CurrencyDto.USD);
        assertNull(result);
    }
}
