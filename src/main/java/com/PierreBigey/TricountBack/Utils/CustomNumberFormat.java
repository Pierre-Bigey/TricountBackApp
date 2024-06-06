package com.PierreBigey.TricountBack.Utils;



public class CustomNumberFormat {

    public static Double format(double amount) {
        return Math.round(amount*100)*0.01;
    }
}
