package com.example.payslip.domain;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import com.example.payslip.domain.Tablerow;

@Entity
public class Taxtable {
    private @Id @GeneratedValue Long tableId;

    @OneToMany
    private List<Tablerow> tableBody;

    public Taxtable() {}

    public Taxtable(List<Tablerow> tableBody) {
        this.tableBody = tableBody;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getTableId() {
        return tableId;
    }

    public List<Tablerow> getTableBody() {
        return tableBody;
    }

    public void setTableBody(List<Tablerow> tableBody) {
        this.tableBody = tableBody;
    }
}
