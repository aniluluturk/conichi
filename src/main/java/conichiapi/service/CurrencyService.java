package conichiapi.service;

import com.google.gson.Gson;
import conichiapi.model.CurrencyResponse;
import conichiapi.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
public class CurrencyService extends AbstractService {
    private final static Logger LOGGER = Logger.getLogger(CurrencyService.class.getName());

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    private RestTemplate restTemplate;

    public CurrencyResponse convertCurrency(String sourceCurrency, String targetCurrency, BigDecimal amount) {
        CurrencyResponse prevConversion = currencyRepository.findBySourceCurrencyAndTargetCurrencyOrderByIdDesc(sourceCurrency, targetCurrency);
        if (prevConversion != null && Math.abs(prevConversion.getTimestamp() - Instant.now().toEpochMilli()) < 900000) { //15 min expire for fetching currency info once again
            LOGGER.info("Found previous request: " + sourceCurrency + " " + targetCurrency);
            LOGGER.info("Previous request timestamp: " + prevConversion.getTimestamp() + " - now: " + Instant.now().toEpochMilli() +
                    " " + Math.abs(prevConversion.getTimestamp() - Instant.now().toEpochMilli()));
            BigDecimal multiplier = prevConversion.getConversionMultiplier();
            BigDecimal convertedAmount = multiplier.multiply(amount);
            return new CurrencyResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
        }

        String fooResourceUrl = String.format("https://free.currconv.com/api/v7/convert?q=%s_%s&compact=ultra&apiKey=b784e053d11c0e443630", sourceCurrency, targetCurrency);
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Gson gson = new Gson();

        HashMap<String, Object> responseMap = gson.fromJson(response.getBody(), HashMap.class);
        BigDecimal multiplier = new BigDecimal(responseMap.get(String.format("%s_%s", sourceCurrency, targetCurrency)).toString());
        BigDecimal convertedAmount = multiplier.multiply(amount);

        CurrencyResponse currencyResponseObj = new CurrencyResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
        currencyRepository.save(currencyResponseObj);
        return new CurrencyResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
    }
}
