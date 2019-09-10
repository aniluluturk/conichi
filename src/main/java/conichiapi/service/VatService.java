package conichiapi.service;

import com.google.gson.Gson;
import conichiapi.model.VatResponse;
import conichiapi.repository.VatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.logging.Logger;

@Component
public class VatService extends AbstractService {
    private final static Logger LOGGER = Logger.getLogger(VatService.class.getName());

    @Autowired
    VatRepository vatRepository;

    @Autowired
    private RestTemplate restTemplate;

    public VatResponse validateVat(String vatNumber) {
        if (vatRepository.findByVatNumber(vatNumber) != null) {
            LOGGER.info("Found previous request: " + vatNumber);
            return vatRepository.findByVatNumber(vatNumber);
        }

        String fooResourceUrl = String.format("http://www.apilayer.net/api/validate?access_key=db99e51055d37dfb0f3bf75810be6bd4&format=1&vat_number=%s", vatNumber);
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        Gson gson = new Gson();
        HashMap<String, Object> responseMap = gson.fromJson(response.getBody(), HashMap.class);

        VatResponse vatResponseObj = new VatResponse(vatNumber, responseMap.get("country_code").toString(), Boolean.valueOf(responseMap.get("valid").toString()));
        vatRepository.save(vatResponseObj);
        return vatResponseObj;
    }
}
