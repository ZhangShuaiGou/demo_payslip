package com.example.payslip.domain;

import java.util.Arrays;

public class Payroll {
    private Employee employee;
    private String fromDate;
    private String toDate;

    public Payroll(Employee employee){
        setEmployee(employee);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getFromDate() {
        String[] str = employee.getPayPeriod().split(" ");
        return str[0] + " " + str[1];
    }

    public String getToDate() {
        String[] str = employee.getPayPeriod().split(" ");
        return str[str.length-2] + " " +  str[str.length-1];
    }

    public int getGrossIncome(){
        double gross = employee.getAnnualSalary() / 12 * employee.getPaymentMonth();
        double diff = gross - (int)gross;

        return (int)(diff>=0.5 ? gross+1 : gross);
    }

    public int getIncomeTax(){
        int annualSalary = employee.getAnnualSalary();
        if(annualSalary<18200){
            return 0;
        }
        else if(annualSalary<37000){
            double tax = ((annualSalary-18200)*0.19) / 12;
            double diff = tax - (int)tax;
            return (int)(diff>=0.5 ? tax+1 : tax);
        }
        else if(annualSalary < 87000){
            double tax = (3572 + (annualSalary-37000)*0.325) / 12;
            double diff = tax - (int)tax;
            return (int)(diff>=0.5 ? tax+1 : tax);
        }
        else if(annualSalary < 180000){
            double tax = (19822 + (annualSalary-87000)*0.37) / 12;
            double diff = tax - (int)tax;
            return (int)(diff>=0.5 ? tax+1 : tax);
        }
        else{
            double tax = (54232 + (annualSalary-180000)*0.45) / 12;
            double diff = tax - (int)tax;
            return (int)(diff>=0.5 ? tax+1 : tax);
        }
    }

    public int getSuper(){
        double _super = getGrossIncome() * getEmployee().getSuperRate();
        double diff = _super - (int)_super;
        return (int)(diff>=0.5 ? _super+1 : _super);
    }

    public int getNetIncome(){
        return getGrossIncome() - getIncomeTax();
    }

    @Override
    public  String toString(){
        return "Employee: " + '\n'
                + employee.toString() + '\n'
                + "fromDate: " + getFromDate() + '\n'
                + "toDate: " + getToDate() + '\n'
                + "grossIncome: " + getGrossIncome() + '\n'
                + "incomeTax: " + getIncomeTax() + '\n'
                + "superannuation: " + getSuper() + '\n'
                + "netIncome: " + getNetIncome() + '\n'
                ;
    }
}
