package conichiapi.controller;

import com.google.gson.Gson;
import conichiapi.model.ConversionResponse;
import conichiapi.model.TimeResponse;
import conichiapi.model.VatResponse;
import conichiapi.repository.ConversionRepository;
import conichiapi.repository.VatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
public class APIController {
    private final static Logger LOGGER = Logger.getLogger(APIController.class.getName());

    @Autowired
    VatRepository vatRepository;

    @Autowired
    ConversionRepository conversionRepository;

    @GetMapping("/time")
    public TimeResponse getCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        return new TimeResponse(localTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a")), localTime.toString());
    }

    @GetMapping("/api/vat/validate")
    public VatResponse validateVat(@RequestParam(name = "vat_number") String vatNumber) {
        if (vatRepository.findByVatNumber(vatNumber) != null) {
            LOGGER.info("Found previous request: " + vatNumber);
            return vatRepository.findByVatNumber(vatNumber);
        }

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = String.format("http://www.apilayer.net/api/validate?access_key=db99e51055d37dfb0f3bf75810be6bd4&format=1&vat_number=%s", vatNumber);
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Gson gson = new Gson();
        HashMap<String, Object> responseMap = gson.fromJson(response.getBody(), HashMap.class);

        VatResponse vatResponseObj = new VatResponse(vatNumber, responseMap.get("country_code").toString(), Boolean.valueOf(responseMap.get("valid").toString()));
        vatRepository.save(vatResponseObj);
        return vatResponseObj;
    }

    @GetMapping("/api/currency/convert")
    public ConversionResponse convertCurrency(@RequestParam(name = "source_currency") String sourceCurrency, @RequestParam(name = "target_currency") String targetCurrency, @RequestParam BigDecimal amount) {
        ConversionResponse prevConversion = conversionRepository.findBySourceCurrencyAndTargetCurrencyOrderByIdDesc(sourceCurrency, targetCurrency);
        if (prevConversion != null && Math.abs(prevConversion.getTimestamp() - Instant.now().toEpochMilli()) < 900000) { //15 min expire for fetching currency info once again
            LOGGER.info("Found previous request: " + sourceCurrency + " " + targetCurrency);
            LOGGER.info("Previous request timestamp: " + prevConversion.getTimestamp() + " - now: " + Instant.now().toEpochMilli() +
                    " " + Math.abs(prevConversion.getTimestamp() - Instant.now().toEpochMilli()));
            BigDecimal multiplier = prevConversion.getConversionMultiplier();
            BigDecimal convertedAmount = multiplier.multiply(amount);
            return new ConversionResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
        }

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = String.format("https://free.currconv.com/api/v7/convert?q=%s_%s&compact=ultra&apiKey=b784e053d11c0e443630", sourceCurrency, targetCurrency);
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Gson gson = new Gson();

        HashMap<String, Object> responseMap = gson.fromJson(response.getBody(), HashMap.class);
        BigDecimal multiplier = new BigDecimal(responseMap.get(String.format("%s_%s", sourceCurrency, targetCurrency)).toString());
        BigDecimal convertedAmount = multiplier.multiply(amount);

        ConversionResponse conversionResponseObj = new ConversionResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
        conversionRepository.save(conversionResponseObj);
        return new ConversionResponse(sourceCurrency, targetCurrency, amount, multiplier, convertedAmount);
    }

}
