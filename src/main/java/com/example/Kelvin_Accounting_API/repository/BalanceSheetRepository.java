package com.example.Kelvin_Accounting_API.repository;

import com.example.Kelvin_Accounting_API.model.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing balance sheets.
 * <p>
 * Provides CRUD operations and additional query capabilities for the {@link BalanceSheet} entity.
 */
@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long> {
    // Custom queries can be added here if needed.
}
