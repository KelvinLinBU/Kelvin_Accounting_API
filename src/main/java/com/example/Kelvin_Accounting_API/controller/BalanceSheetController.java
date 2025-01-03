package com.example.Kelvin_Accounting_API.controller;

import com.example.Kelvin_Accounting_API.model.BalanceSheet;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Asset;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Equity;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Liability;
import com.example.Kelvin_Accounting_API.service.BalanceSheetService;
import org.springframework.http.MediaType;
import com.example.Kelvin_Accounting_API.util.BalanceSheetPdfGenerator;

import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing balance sheets.
 * 
 * <p>This controller provides endpoints to create, retrieve, update, and delete
 * balance sheets. It interacts with the {@link BalanceSheetService} to perform
 * business logic and return appropriate HTTP responses.</p>
 * 
 * <p>Base URL: <b>/balancesheets</b></p>
 * 
 * @author KelvinLinBU
 * @version 1.0
 * @since 2025-01-02
 */
@RestController
@RequestMapping("/balancesheets")
public class BalanceSheetController {

    private final BalanceSheetService balanceSheetService;
    private final BalanceSheetPdfGenerator balanceSheetPdfGenerator;


    /**
     * Constructor for injecting the BalanceSheetService dependency.
     *
     * @param balanceSheetService The service handling balance sheet operations.
     * @param balanceSheetPdfGenerator Utility to generate PDF files.
     */
    public BalanceSheetController(BalanceSheetService balanceSheetService, BalanceSheetPdfGenerator balanceSheetPdfGenerator) {
        this.balanceSheetService = balanceSheetService;
        this.balanceSheetPdfGenerator = balanceSheetPdfGenerator;
    }

    /**
     * Creates a new balance sheet.
     * 
     * <p>Accepts a balance sheet object in the request body and saves it to the database.</p>
     *
     * @param balanceSheet The balance sheet data provided in the request body.
     * @return The created balance sheet with HTTP 201 (Created) status.
     */
  @PostMapping
public BalanceSheet createBalanceSheet(@RequestBody BalanceSheet balanceSheet) {
    // Ensure the company name is set
    if (balanceSheet.getCompany_name() == null || balanceSheet.getCompany_name().trim().isEmpty()) {
        balanceSheet.setCompany_name("ABC Corp.");  // Set default value
    }

    // Ensure the relationship is set for Assets
    double totalAssets = 0.0;
    if (balanceSheet.getAssets() != null) {
        for (Asset asset : balanceSheet.getAssets()) {
            asset.setBalanceSheet(balanceSheet);
            totalAssets += asset.getValue();  // Sum total assets
        }
    }

    // Ensure the relationship is set for Liabilities
    double totalLiabilities = 0.0;
    if (balanceSheet.getLiabilities() != null) {
        for (Liability liability : balanceSheet.getLiabilities()) {
            liability.setBalanceSheet(balanceSheet);
            totalLiabilities += liability.getValue();  // Sum total liabilities
        }
    }

    // Ensure the relationship is set for Equities
    double totalEquities = 0.0;
    if (balanceSheet.getEquities() != null) {
        for (Equity equity : balanceSheet.getEquities()) {
            equity.setBalanceSheet(balanceSheet);
            totalEquities += equity.getValue();  // Sum total equities
        }
    }

    // Check if total assets equal total liabilities + total equities
    double totalLiabilitiesAndEquities = totalLiabilities + totalEquities;
    if (totalAssets != totalLiabilitiesAndEquities) {
        double difference = totalAssets - totalLiabilitiesAndEquities;

        // Add an adjustment entry to equities to balance the balance sheet
        Equity adjustmentEquity = new Equity("Reconciliation Adjustment", difference, balanceSheet);
        if (balanceSheet.getEquities() == null) {
            balanceSheet.setEquities(new ArrayList<>());
        }
        balanceSheet.getEquities().add(adjustmentEquity);
    }

    // Save the balance sheet
    return balanceSheetService.createBalanceSheet(balanceSheet);
}



  @GetMapping("/{id}/pdf")
public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
    Optional<BalanceSheet> balanceSheetOptional = balanceSheetService.getBalanceSheetById(id);
    if (balanceSheetOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    BalanceSheet balanceSheet = balanceSheetOptional.get();
    byte[] pdfData = balanceSheetPdfGenerator.generatePdf(balanceSheet, "balance_sheet_" + id + ".pdf");
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=balance_sheet_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfData);
}

    /**
     * Retrieves all balance sheets.
     * 
     * <p>Returns a list of all balance sheets stored in the database.</p>
     *
     * @return A list of balance sheets with HTTP 200 (OK) status.
     */
    @GetMapping
    public ResponseEntity<List<BalanceSheet>> getAllBalanceSheets() {
        List<BalanceSheet> balanceSheets = balanceSheetService.getAllBalanceSheets();
        return ResponseEntity.ok(balanceSheets);
    }

    /**
     * Retrieves a balance sheet by its ID.
     * 
     * <p>If the balance sheet with the specified ID exists, it is returned. Otherwise, a 404
     * (Not Found) status is returned.</p>
     *
     * @param id The ID of the balance sheet to retrieve.
     * @return The balance sheet with HTTP 200 (OK) status if found, or HTTP 404 (Not Found) if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BalanceSheet> getBalanceSheetById(@PathVariable Long id) {
        return balanceSheetService.getBalanceSheetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing balance sheet by its ID.
     * 
     * <p>If the balance sheet with the specified ID exists, it is updated with the provided data.
     * Otherwise, a 404 (Not Found) status is returned.</p>
     *
     * @param id The ID of the balance sheet to update.
     * @param updatedBalanceSheet The updated balance sheet data provided in the request body.
     * @return The updated balance sheet with HTTP 200 (OK) status if successful, or HTTP 404 (Not Found) if the balance sheet is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BalanceSheet> updateBalanceSheet(
            @PathVariable Long id,
            @RequestBody BalanceSheet updatedBalanceSheet
    ) {
        return balanceSheetService.updateBalanceSheet(id, updatedBalanceSheet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a balance sheet by its ID.
     * 
     * <p>If the balance sheet with the specified ID exists, it is deleted. Otherwise, a 404
     * (Not Found) status is returned.</p>
     *
     * @param id The ID of the balance sheet to delete.
     * @return HTTP 204 (No Content) if the deletion is successful, or HTTP 404 (Not Found) if the balance sheet is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalanceSheet(@PathVariable Long id) {
        if (balanceSheetService.deleteBalanceSheet(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
