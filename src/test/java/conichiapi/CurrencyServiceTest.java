package conichiapi;

import conichiapi.model.CurrencyResponse;
import conichiapi.repository.CurrencyRepository;
import conichiapi.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyService currencyService = new CurrencyService();

    @Mock
    CurrencyRepository currencyRepository;

    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
        //CurrencyResponse emp = new CurrencyResponse("TRY", "EUR", new BigDecimal("123"), new BigDecimal("456"), new BigDecimal("999"));
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("TRY_EUR", 123.456);
        Mockito
                .when(restTemplate.getForEntity("http://localhost:8080/employee/E001", CurrencyResponse.class))
                .thenReturn(new ResponseEntity(responseMap, HttpStatus.OK));
        Mockito.when(currencyRepository.save(new CurrencyResponse())).thenReturn(new CurrencyResponse());

        CurrencyResponse currencyResponse = currencyService.convertCurrency("TRY", "EUR", new BigDecimal("123"));
        assertEquals(currencyResponse.getConversionMultiplier(), new BigDecimal(123.456));
    }
}