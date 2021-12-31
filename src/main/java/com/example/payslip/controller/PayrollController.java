package com.example.payslip.controller;

import com.example.payslip.domain.Employee;
import com.example.payslip.domain.Payroll;
import com.example.payslip.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@ResponseBody
public class PayrollController {

    private final TaxtableRepository taxRepository;
    private final TablerowRepository rowRepository;

    PayrollController(TaxtableRepository taxRepository, TablerowRepository rowRepository){
        this.taxRepository = taxRepository;
        this.rowRepository = rowRepository;
    }

    @GetMapping("/taxtable")
    ResponseEntity alltable() {
        if(taxRepository.count()==0)
            return ResponseEntity.ok("There is no tax table! Please input tax table first (POST /taxtable)!");
        else
            return ResponseEntity.ok(taxRepository.findAll());
    }

    @GetMapping("/tablerows")
    List<Tablerow> allrow() {
        return rowRepository.findAll();
    }

    @PostMapping("/taxtable")
    ResponseEntity newTaxtable(@RequestBody List<Tablerow> tableRows) {
        Long size = taxRepository.count();
        if(size==1){
            List<Taxtable> temp = taxRepository.findAll();
            return ResponseEntity.ok("The tax table is exist, please check it (GET /taxtable) or update it(PUT /taxtable/{id}). The current table Id is: " + temp.get(0).getTableId());
        }
        else {
            rowRepository.saveAll(tableRows);
            return ResponseEntity.ok(taxRepository.save(new Taxtable(tableRows)));
        }
    }

    @PutMapping("/taxtable/{id}")
    ResponseEntity replaceTaxtable(@RequestBody List<Tablerow> tableRows, @PathVariable Long id) {
        return ResponseEntity.ok(taxRepository.findById(id).map(taxtable -> {
            taxtable.setTableBody(rowRepository.saveAll(tableRows));
            return taxRepository.save(taxtable);
        }));
    }

    @DeleteMapping("/taxtable")
    ResponseEntity deleteTaxtable(){
        if(taxRepository.count()==0){
            return ResponseEntity.ok("There is no tax table! Please input tax table first (POST /taxtable)!");
        }
        taxRepository.deleteAll();
        return ResponseEntity.ok("Current tax table has been deleted!");
    }

    @PostMapping("/payroll")
    public ResponseEntity PostBody(@RequestBody List<Employee> employees) {
        if(taxRepository.count()==0){
            return ResponseEntity.ok("There is no tax table! Please input tax table first (POST /taxtable)!");
        }
        else {
            List<Payroll> resPayroll = new ArrayList<>();
            List<Taxtable> resTaxtable = taxRepository.findAll();

            for (Employee employee : employees) {
                resPayroll.add(new Payroll(employee, resTaxtable.get(0)));
            }
            return ResponseEntity.ok(resPayroll);
        }
    }
}
