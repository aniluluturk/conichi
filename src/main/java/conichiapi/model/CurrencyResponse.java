package conichiapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class CurrencyResponse {
    private String sourceCurrency;
    private String targetCurrency;
    @Column(precision = 34, scale = 6)
    private BigDecimal conversionMultiplier;
    @Column(precision = 34, scale = 6)
    private BigDecimal sourceAmount;
    @Column(precision = 34, scale = 6)
    private BigDecimal totalCalculatedAmount;
    @JsonIgnore
    private Long timestamp;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Status status;

    public CurrencyResponse() {
        status = Status.FAIL;
    }

    public CurrencyResponse(String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal conversionMultiplier, BigDecimal targetAmount) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.conversionMultiplier = conversionMultiplier;
        this.sourceAmount = sourceAmount;
        this.totalCalculatedAmount = targetAmount;
        this.timestamp = Instant.now().toEpochMilli();
        this.status = Status.SUCCESS;
    }

    @JsonProperty("source_currency")
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    @JsonProperty("target_currency")
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @JsonProperty("conversion_multiplier")
    public BigDecimal getConversionMultiplier() {
        return conversionMultiplier;
    }

    public void setConversionMultiplier(BigDecimal conversionMultiplier) {
        this.conversionMultiplier = conversionMultiplier;
    }

    @JsonProperty("amount")
    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    @JsonProperty("target_amount")
    public BigDecimal getTotalCalculatedAmount() {
        return totalCalculatedAmount;
    }

    public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
        this.totalCalculatedAmount = totalCalculatedAmount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}