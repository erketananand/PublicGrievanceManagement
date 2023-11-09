package com.scaler.grievance.repositories;

import com.scaler.grievance.constants.*;
import com.scaler.shared.constants.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scaler.grievance.entities.Grievance;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Long> {

    @Value("${custom.query.unassignedGrievances}")
    String unassignedGrievances = null;
    <S extends Grievance> S save(S entity);
    Optional<Grievance> findById(Long id);

    List<Grievance> findByUserId(Long userId);

    List<Grievance> findByStatus(GrievanceStatus status);

    List<Grievance> findByPriority(GrievancePriority priority);

    List<Grievance> findByCategoryAndStatus(GrievanceCategory category, GrievanceStatus status);

    List<Grievance> findByTitleContainingOrDescriptionContaining(String title, String description);

    // Custom query to search grievances by multiple criteria
    @Query(value = "(:query)", nativeQuery = true)
    List<Grievance> searchGrievances(String query);

    @Query(value = CustomQueries.UNASSIGNED_GRIEVANCES, nativeQuery = true)
    List<Grievance> getUnassignedGrievances(String superAdminRole);

    @Query(value = CustomQueries.SOFT_DELETE_GRIEVANCE, nativeQuery = true)
    void softDeleteGrievance(Long grievanceId);

}

