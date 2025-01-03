package com.example.Kelvin_Accounting_API.service;

import com.example.Kelvin_Accounting_API.model.BalanceSheet;
import com.example.Kelvin_Accounting_API.repository.BalanceSheetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing balance sheets.
 * 
 * <p>This service handles the business logic for creating, retrieving, updating,
 * and deleting balance sheets. It interacts with the {@link BalanceSheetRepository}
 * to perform database operations.</p>
 * 
 * @author KelvinLinBU
 * @version 1.0
 * @since 2025-01-02
 */
@Service
public class BalanceSheetService {

    private final BalanceSheetRepository balanceSheetRepository;

    /**
     * Constructor for injecting the BalanceSheetRepository dependency.
     *
     * @param balanceSheetRepository The repository for accessing balance sheet data.
     */
    public BalanceSheetService(BalanceSheetRepository balanceSheetRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
    }

    /**
     * Creates a new balance sheet and saves it to the database.
     *
     * @param balanceSheet The balance sheet to create.
     * @return The created balance sheet.
     */
    public BalanceSheet createBalanceSheet(BalanceSheet balanceSheet) {
        return balanceSheetRepository.save(balanceSheet);
    }

    /**
     * Retrieves all balance sheets from the database.
     *
     * @return A list of all balance sheets.
     */
    public List<BalanceSheet> getAllBalanceSheets() {
        return balanceSheetRepository.findAll();
    }

    /**
     * Retrieves a balance sheet by its ID.
     *
     * @param id The ID of the balance sheet to retrieve.
     * @return An {@link Optional} containing the balance sheet if found, or empty if not found.
     */
    public Optional<BalanceSheet> getBalanceSheetById(Long id) {
        return balanceSheetRepository.findById(id);
    }

    /**
     * Updates an existing balance sheet by its ID.
     *
     * <p>If the balance sheet exists, its fields are updated with the provided data.</p>
     *
     * @param id The ID of the balance sheet to update.
     * @param updatedBalanceSheet The updated balance sheet data.
     * @return An {@link Optional} containing the updated balance sheet if successful, or empty if the ID does not exist.
     */
    public Optional<BalanceSheet> updateBalanceSheet(Long id, BalanceSheet updatedBalanceSheet) {
        return balanceSheetRepository.findById(id).map(existingBalanceSheet -> {
            existingBalanceSheet.setCompany_name(updatedBalanceSheet.getCompany_name()); 
            existingBalanceSheet.setDate(updatedBalanceSheet.getDate());
            existingBalanceSheet.setAssets(updatedBalanceSheet.getAssets());
            existingBalanceSheet.setLiabilities(updatedBalanceSheet.getLiabilities());
            existingBalanceSheet.setEquities(updatedBalanceSheet.getEquities());
            return balanceSheetRepository.save(existingBalanceSheet);
        });
    }

    /**
     * Deletes a balance sheet by its ID.
     *
     * <p>If the balance sheet exists, it is deleted from the database.</p>
     *
     * @param id The ID of the balance sheet to delete.
     * @return {@code true} if the deletion was successful, or {@code false} if the balance sheet was not found.
     */
    public boolean deleteBalanceSheet(Long id) {
        if (balanceSheetRepository.existsById(id)) {
            balanceSheetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
