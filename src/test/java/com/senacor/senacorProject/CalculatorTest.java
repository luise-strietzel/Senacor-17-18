package com.senacor.senacorProject;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator sut = new Calculator();

    @Test
    public void addTest(){
        int result = sut.add(10,5);

        Assert.assertEquals("5+5 should be 15", 15, result);
    }
}
