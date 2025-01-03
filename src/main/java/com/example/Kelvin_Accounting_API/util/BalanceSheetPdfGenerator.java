package com.example.Kelvin_Accounting_API.util;

import com.example.Kelvin_Accounting_API.model.BalanceSheet;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Asset;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Equity;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Liability;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory; 
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.io.ByteArrayOutputStream;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.colors.ColorConstants;

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
            String calibriFontPath = "resources/calibri-font-family/calibri-regular.ttf";
            PdfFont calibriFont = PdfFontFactory.createFont(calibriFontPath, "WinAnsi", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

        // Set the font for the document
        document.setFont(calibriFont);
            // Add content to the PDF
            document.add(new Paragraph(balanceSheet.getCompany_name()).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold().setFontColor(ColorConstants.BLACK).setMarginBottom(-10)); 
            document.add(new Paragraph("Balance Sheet at " + balanceSheet.getDate()).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold().setFontColor(ColorConstants.BLACK));
            SolidLine line = new SolidLine(1f);  // Line thickness
        LineSeparator separator = new LineSeparator(line).setFontColor(ColorConstants.BLACK);
        separator.setMarginBottom(10);  // Add margin below the line
        separator.setMarginTop(10); 
            document.add(separator); 
            document.add(new Paragraph("Assets:").setBold());
            Double total_assets = 0.0;
            for (Asset asset : balanceSheet.getAssets()) {
                document.add(new Paragraph(asset.getName() + ": $" + asset.getValue()).setFontColor(ColorConstants.BLUE));
                total_assets += asset.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Assets: $" + String.valueOf(total_assets)).setBold()); 
            document.add(separator); 
            document.add(new Paragraph("Liabilities:").setBold());
            Double total_liabilities = 0.0; 
            for (Liability liability : balanceSheet.getLiabilities()) {
                document.add(new Paragraph(liability.getName() + ": $" + liability.getValue()).setFontColor(ColorConstants.BLUE));
                total_liabilities += liability.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Liabilities: $" + String.valueOf(total_liabilities)).setBold()); 
            document.add(separator); 
            Double total_equities = 0.0; 
            document.add(new Paragraph("Equities:").setBold());
            for (Equity equity : balanceSheet.getEquities()) {
                document.add(new Paragraph(equity.getName() + ": $" + equity.getValue()).setFontColor(ColorConstants.BLUE));
                total_equities += equity.getValue();  // Increment value
            }
            document.add(new Paragraph("Total Equities: $" + String.valueOf(total_equities)).setBold()); 
            document.add(separator); 
            document.add(new Paragraph("Total Liabilities and Equities: $" + String.valueOf((total_equities + total_liabilities))).setBold()); 
            // Close the document
            document.close();

            // Return the PDF data as a byte array
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF for balance sheet", e);
        }
    }
}
