package conichiapi.controller;

import conichiapi.model.CurrencyResponse;
import conichiapi.model.TimeResponse;
import conichiapi.model.VatResponse;
import conichiapi.service.CurrencyService;
import conichiapi.service.VatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class APIController {
    private final static Logger LOGGER = Logger.getLogger(APIController.class.getName());

    @Autowired
    CurrencyService currencyService;

    @Autowired
    VatService vatService;

    @GetMapping("/time")
    public TimeResponse getCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        return new TimeResponse(localTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a")), localTime.toString());
    }

    @GetMapping("/api/vat/validate")
    public VatResponse validateVat(@RequestParam(name = "vat_number") String vatNumber) {
        VatResponse response = new VatResponse(); //default with status->fail
        try {
            response = vatService.validateVat(vatNumber);
        } catch (Exception e) {
            LOGGER.severe("Cannot process VAT Validate API request");
        }

        return response;
    }

    @GetMapping("/api/currency/convert")
    public CurrencyResponse convertCurrency(@RequestParam(name = "source_currency") String sourceCurrency, @RequestParam(name = "target_currency") String targetCurrency, @RequestParam BigDecimal amount) {
        CurrencyResponse response = new CurrencyResponse(); //default with status->fail

        try {
            response = currencyService.convertCurrency(sourceCurrency, targetCurrency, amount);
        } catch (Exception e) {
            LOGGER.severe("Cannot process Currency Convert API request");
        }

        return response;
    }

}
