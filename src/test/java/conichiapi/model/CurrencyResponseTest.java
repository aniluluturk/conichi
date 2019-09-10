package conichiapi.model;

import conichiapi.model.Status;
import conichiapi.model.CurrencyResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyResponseTest {

    @Test
    void defaultConstructorShouldSetStatusToFail() {
        CurrencyResponse v = new CurrencyResponse();

        assertEquals(v.getStatus(), Status.FAIL);
        assertNull(v.getTimestamp());
        assertNull(v.getConversionMultiplier());
        assertNull(v.getSourceAmount());
        assertNull(v.getSourceCurrency());
        assertNull(v.getTargetCurrency());
        assertNull(v.getTotalCalculatedAmount());
    }

    @Test
    void parametrizedConstructorShouldSetStatusToSuccess() {
        CurrencyResponse v = new CurrencyResponse("TRY", "EUR", new BigDecimal("123"), new BigDecimal("456"), new BigDecimal("999"));

        assertEquals(v.getStatus(), Status.SUCCESS);
        assertNotNull(v.getTimestamp());
        assertNotNull(v.getConversionMultiplier());
        assertNotNull(v.getSourceAmount());
        assertNotNull(v.getSourceCurrency());
        assertNotNull(v.getStatus());
        assertNotNull(v.getTargetCurrency());
        assertNotNull(v.getTotalCalculatedAmount());
    }

    @Test
    void parametrizedConstructorShouldSetFields() {
        CurrencyResponse v = new CurrencyResponse("TRY", "EUR", new BigDecimal("123"), new BigDecimal("456"), new BigDecimal("999"));

        assertEquals(v.getStatus(), Status.SUCCESS);
        assertTrue(Instant.now().toEpochMilli() - v.getTimestamp() < 60000); //tests shouldn't run 60 seconds, so...
        assertEquals(v.getConversionMultiplier().intValue(), 456 );
        assertEquals(v.getSourceAmount().intValue(), 123);
        assertEquals(v.getSourceCurrency(), "TRY");
        assertEquals(v.getStatus(), Status.SUCCESS);
        assertEquals(v.getTargetCurrency(), "EUR");
        assertEquals(v.getTotalCalculatedAmount().intValue(), 999);
    }


}