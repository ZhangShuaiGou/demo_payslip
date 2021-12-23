package com.example.payslip.controller;

import com.example.payslip.domain.Employee;
import com.example.payslip.domain.Payroll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@ResponseBody
public class PayrollController {
    @PostMapping("/employee")
    public ResponseEntity<List<Payroll>> postBody(@RequestBody List<Employee> employees) {
        List<Payroll> resPayroll = new ArrayList<Payroll>();
        for(Employee employee : employees){
            resPayroll.add(new Payroll(employee));
        }
        return ResponseEntity.ok(resPayroll);
    }
}
