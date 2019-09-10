package conichiapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import conichiapi.model.CurrencyResponse;
import conichiapi.model.VatResponse;
import conichiapi.repository.CurrencyRepository;
import conichiapi.repository.VatRepository;
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
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class VatServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VatService vatService = new VatService();

    @Mock
    VatRepository vatRepository;

    @Test
    public void mockedVatServiceShouldReturnProvidedCountryCodeAndStatus() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("country_code", "IL");
        responseMap.put("valid", false);
        Gson gson = new GsonBuilder().create();
        String serializedMap = gson.toJson(responseMap);
        Mockito
                .when(restTemplate.getForEntity(any(String.class), any()))
                .thenReturn(new ResponseEntity(serializedMap, HttpStatus.OK));
        Mockito.when(vatRepository.save(any(VatResponse.class))).thenReturn(new VatResponse());

        VatResponse currencyResponse = vatService.validateVat("TR123456");
        assertEquals(currencyResponse.getValid(), false);
        assertEquals(currencyResponse.getCountryCode(), "IL");
    }
}