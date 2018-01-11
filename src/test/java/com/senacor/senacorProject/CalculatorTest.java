package com.senacor.senacorProject;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator sut = new Calculator();

    @Test
    public void addTest(){
        int result = sut.add(10,20);

        Assert.assertEquals("10+20 should be 30", 30, result);
    }
}

