package com.senacor.senacorProject;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator sut = new Calculator();

    @Test
    public void addTest(){
        int result = sut.add(1,2);

        Assert.assertEquals("1+2 should be 3", 3, result);
    }
}
