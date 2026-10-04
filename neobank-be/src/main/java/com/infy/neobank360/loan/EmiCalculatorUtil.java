package com.infy.neobank360.loan;

public class EmiCalculatorUtil {

    public static double calculateEMI(double principal, double annualRate, int months) 
    {

        double r = annualRate / 12 / 100;

        double emi = (principal * r * Math.pow(1 + r, months)) /
                     (Math.pow(1 + r, months) - 1);

        return Math.round(emi * 100.0) / 100.0;
    }
}
