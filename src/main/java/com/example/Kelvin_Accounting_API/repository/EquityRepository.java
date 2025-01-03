package com.example.Kelvin_Accounting_API.repository;

import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Equity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing equities.
 * <p>
 * Provides CRUD operations and additional query capabilities for the {@link Equity} entity.
 * 
 * @author KelvinLinBU
 * @version 1.0
 * @since 2025-01-02
 */
@Repository
public interface EquityRepository extends JpaRepository<Equity, Long> {
    // Custom queries can be added here if needed.
}
