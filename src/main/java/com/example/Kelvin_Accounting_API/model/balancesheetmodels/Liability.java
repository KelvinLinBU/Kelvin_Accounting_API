package com.example.Kelvin_Accounting_API.model.balancesheetmodels;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import com.example.Kelvin_Accounting_API.model.BalanceSheet;

/**
 * Model class for an asset
 * in a rudimentary accounting software
 * <p>Liabilities is something that a company owes and are settled over time through transfer of economic benefits. 
 * This class maps to the "liabilities" table in the database.</p>
 * 
 * @author KelvinLinBU
 * @version 1.0
 * @since 2025-01-02
 */

 @Entity
 @Table(name = "liabilities") //call table liabilities
public class Liability {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    private Long id; 

    @Column(nullable = false)
    private String name; 

    @Column(nullable = false)
    private Double value; 

    @ManyToOne @JoinColumn(name = "balance_sheet_id", nullable = false)
    @JsonBackReference("liability-balanceSheet")
    private BalanceSheet balanceSheet; 

    public Liability() {}

    public Liability(String name, Double value, BalanceSheet balanceSheet) {
        this.name = name;
        this.value = value;
        this.balanceSheet = balanceSheet;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }
}
