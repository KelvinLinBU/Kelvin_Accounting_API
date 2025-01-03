package com.example.Kelvin_Accounting_API.util;

import com.example.Kelvin_Accounting_API.model.BalanceSheet;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Asset;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Equity;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Liability;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Utility class for generating a PDF representation of a balance sheet.
 * @author KelvinLinBU
 * @version 1.0
 * @since 2025-01-02
 */
@Component
public class BalanceSheetPdfGenerator {

    /**
     * Generates a PDF document for the provided balance sheet.
     *
     * @param balanceSheet The balance sheet to generate the PDF for.
     * @param filename The name of the generated PDF file.
     * @return A byte array containing the generated PDF data.
     */
    public byte[] generatePdf(BalanceSheet balanceSheet, String filename) {
        // Create a ByteArrayOutputStream to hold the PDF data
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Initialize PdfWriter and PdfDocument
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Add content to the PDF
            document.add(new Paragraph(balanceSheet.getCompany_name())); 
            document.add(new Paragraph("Balance Sheet at " + balanceSheet.getDate()));
            document.add(new Paragraph("Assets:"));
            Double total_assets = 0.0;
            for (Asset asset : balanceSheet.getAssets()) {
                document.add(new Paragraph(" - " + asset.getName() + ": $" + asset.getValue()));
                total_assets += asset.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Assets: " + String.valueOf(total_assets))); 
            
            document.add(new Paragraph("Liabilities:"));
            Double total_liabilities = 0.0; 
            for (Liability liability : balanceSheet.getLiabilities()) {
                document.add(new Paragraph(" - " + liability.getName() + ": $" + liability.getValue()));
                total_liabilities += liability.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Liabilities: " + String.valueOf(total_liabilities))); 
            
            Double total_equities = 0.0; 
            document.add(new Paragraph("Equities:"));
            for (Equity equity : balanceSheet.getEquities()) {
                document.add(new Paragraph(" - " + equity.getName() + ": $" + equity.getValue()));
                total_equities += equity.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Equities: " + String.valueOf(total_equities))); 
            document.add(new Paragraph("Total Liabilities and Equities: " + String.valueOf((total_equities + total_liabilities)))); 
            // Close the document
            document.close();

            // Return the PDF data as a byte array
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF for balance sheet", e);
        }
    }
}
