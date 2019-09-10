package conichiapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VatResponse {
    private String countryCode;
    private Boolean valid;
    private String vatNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public VatResponse() {

    }

    public VatResponse(String vatNumber, String countryCode, Boolean valid) {
        this.vatNumber = vatNumber;
        this.countryCode = countryCode;
        this.valid = valid;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("valid")
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("vat_number")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
