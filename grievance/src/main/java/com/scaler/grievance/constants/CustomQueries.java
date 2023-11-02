package com.scaler.grievance.constants;

import com.scaler.grievance.entities.Grievance;

public interface CustomQueries {
    // Custom query to search grievances by multiple criteria
    String SEARCH_GRIEVANCES = "SELECT * FROM grievances g WHERE 1=1" +
            "    (:category IS NULL OR g.category = :category) " +
            "    AND (:subCategory IS NULL OR g.subCategory = :subCategory) " +
            "    AND (:status IS NULL OR g.status = :status) " +
            "    AND (:priority IS NULL OR g.priority = :priority) " +
            "    AND (:keyword IS NULL OR g.title LIKE %:keyword% OR g.description LIKE %:keyword%)";
    String UNASSIGNED_GRIEVANCES = "SELECT * FROM grievances g WHERE (g.admin_id IS NULL OR g.admin_id = :superAdminRole)";
    String SOFT_DELETE_GRIEVANCE = "UPDATE grievances SET status = 'DELETED' WHERE id = ?";
}
