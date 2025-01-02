package com.example.Kelvin_Accounting_API.repository;

import com.example.Kelvin_Accounting_API.model.balancesheetmodels.Liability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing liabilities.
 * <p>
 * Provides CRUD operations and additional query capabilities for the {@link Liability} entity.
 */
@Repository
public interface LiabilityRepository extends JpaRepository<Liability, Long> {
    // Custom queries can be added here if needed.
}
