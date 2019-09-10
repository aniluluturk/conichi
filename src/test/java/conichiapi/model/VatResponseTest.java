package conichiapi.model;

import conichiapi.model.Status;
import conichiapi.model.VatResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VatResponseTest {

    @Test
    void defaultConstructorShouldSetStatusToFail() {
        VatResponse v = new VatResponse();

        assertEquals(v.getStatus(), Status.FAIL);
        assertNull(v.getCountryCode());
        assertNull(v.getValid());
        assertNull(v.getVatNumber());
    }

    @Test
    void parametrizedConstructorShouldSetStatusToSuccess() {
        VatResponse v = new VatResponse("12345", "UK", true);

        assertEquals(v.getStatus(), Status.SUCCESS);
        assertNotNull(v.getCountryCode());
        assertNotNull(v.getValid());
        assertNotNull(v.getVatNumber());
    }

    @Test
    void parametrizedConstructorShouldSetFields() {
        VatResponse v = new VatResponse("12345", "UK", true);

        assertEquals(v.getStatus(), Status.SUCCESS);
        assertEquals(v.getVatNumber(), "12345");
        assertEquals(v.getCountryCode(), "UK");
        assertEquals(v.getValid(), true);
    }

    @Test
    void testSettersAndGetters() {
        VatResponse v = new VatResponse();
        v.setStatus(Status.SUCCESS);
        v.setCountryCode("UK");
        v.setValid(true);
        v.setVatNumber("12345");

        assertEquals(v.getStatus(), Status.SUCCESS);
        assertEquals(v.getVatNumber(), "12345");
        assertEquals(v.getCountryCode(), "UK");
        assertEquals(v.getValid(), true);
    }

}