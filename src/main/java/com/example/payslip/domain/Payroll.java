package com.example.payslip.domain;

import java.util.List;

public class Payroll {
    private Employee employee;
    private String fromDate;
    private String toDate;
    private Taxtable taxtable;

    public Payroll(Employee employee, Taxtable taxtable){
        setEmployee(employee);
        setTaxtable(taxtable);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    protected Taxtable getTaxtable() {
        return taxtable;
    }

    protected void setTaxtable(Taxtable taxtable) {
        this.taxtable = taxtable;
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

    public int getIncomeTax() {
        int annualSalary = employee.getAnnualSalary();
        List<Tablerow> tablebody = taxtable.getTableBody();
        int resTax = 0;
        if (annualSalary <= tablebody.get(0).getMaxTaxThreshold()) {
            return 0;
        }
        else {
            for (int i = 1; i < tablebody.size(); i++) {
                int minT = tablebody.get(i).getMinTaxThreshold();
                int maxT = tablebody.get(i).getMaxTaxThreshold();
                int base = tablebody.get(i).getTaxBase();
                float rate = tablebody.get(i).getTaxRate();
                if (annualSalary >= minT && annualSalary <= maxT) {
                    double tax = (base + (annualSalary - minT + 1) * rate) / 12;
                    double diff = tax - (int) tax;
                    resTax = (int) (diff >= 0.5 ? tax + 1 : tax);
                    break;
                }
            }
        }
        return resTax;
    }

    public int getSuper(){
        double _super = getGrossIncome() * getEmployee().getSuperRate();
        double diff = _super - (int)_super;
        return (int)(diff>=0.5 ? _super+1 : _super);
    }

    public int getNetIncome(){
        return getGrossIncome() - getIncomeTax();
    }
}
