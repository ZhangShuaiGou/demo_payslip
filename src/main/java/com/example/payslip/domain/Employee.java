package com.example.payslip.domain;
import java.util.Arrays;

public class Employee {
    private String firstName;
    private String lastName;
    private int annualSalary;
    private double superRate;
    private String payPeriod;
    private int paymentMonth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(int annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getSuperRate() {
        return superRate;
    }

    public void setSuperRate(double superRate) {
        this.superRate = superRate;
    }

    protected String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public int getPaymentMonth(){
        String[] str = payPeriod.split(" ");
        final String[] monthTable = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return  Arrays.asList(monthTable).indexOf(str[str.length-1]) - Arrays.asList(monthTable).indexOf(str[1]) + 1;
    }

    @Override
    public  String toString(){
        return "first name: " + getFirstName() + '\n'
                + "last name: " + getLastName() + '\n'
                + "annualSalary: " + getAnnualSalary() + '\n'
                + "payment month: " + getPaymentMonth() + '\n'
                + "super rate: " + getSuperRate()
                ;
    }
}
