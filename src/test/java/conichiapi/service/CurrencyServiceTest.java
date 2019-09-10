package conichiapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import conichiapi.model.CurrencyResponse;
import conichiapi.repository.CurrencyRepository;
import conichiapi.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyService currencyService = new CurrencyService();

    @Mock
    CurrencyRepository currencyRepository;

    @Test
    public void mockedCurrencyServiceShouldReturnProvidedMultiplier() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("TRY_EUR", 123.456);
        Gson gson = new GsonBuilder().create();
        String serializedMap = gson.toJson(responseMap);
        Mockito
                .when(restTemplate.getForEntity(any(String.class), any()))
                .thenReturn(new ResponseEntity(serializedMap, HttpStatus.OK));
        Mockito.when(currencyRepository.save(any(CurrencyResponse.class))).thenReturn(new CurrencyResponse());

        CurrencyResponse currencyResponse = currencyService.convertCurrency("TRY", "EUR", new BigDecimal("123"));
        assertEquals(currencyResponse.getConversionMultiplier().doubleValue(), 123.456);
    }
}