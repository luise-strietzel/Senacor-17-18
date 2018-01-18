package com.senacor.senacorProject;

import org.junit.Assert;
import org.junit.Test;

public class KontoTest {

    private Konto sut = new Konto();

    @Test
    public void testGetKontostand() throws Exception
    {
        float result = sut.getKontostand();
        System.out.println(result);

        Assert.assertEquals(0.0, result, 1e-8);
    }

    @Test
    public void testGetLimit() throws Exception
    {
        float result = sut.getLimit();
        System.out.println(result);

        Assert.assertEquals(245000.0, result, 1e-8);
    }
}
