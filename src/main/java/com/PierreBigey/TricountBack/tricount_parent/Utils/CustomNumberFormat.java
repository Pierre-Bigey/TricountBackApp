package com.PierreBigey.TricountBack.tricount_parent.Utils;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomNumberFormat {

    public static double format(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
