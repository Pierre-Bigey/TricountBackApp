package com.PierreBigey.TricountBack.Utils;

import com.PierreBigey.TricountBack.tricount_parent.Utils.CustomNumberFormat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CustomNumberFormatTest {

    private static CustomNumberFormat customNumberFormat;
    @BeforeAll
    public static void init() {
        customNumberFormat = new CustomNumberFormat();
    }
    @Test
    @DisplayName("Test of format with a non decimal number")
    public void TestWithANonDecimalNumber() {
        double numberToTest = 1.0/3.0;
        double formated = customNumberFormat.format(numberToTest);
        Assertions.assertEquals(0.33,formated);
    }

    @Test
    @DisplayName("Test of format with a decimal number")
    public void TestWithADecimalNumber() {
        double numberToTest = 3.1415;
        double formated = customNumberFormat.format(numberToTest);
        Assertions.assertEquals(3.14,formated);
    }


}
