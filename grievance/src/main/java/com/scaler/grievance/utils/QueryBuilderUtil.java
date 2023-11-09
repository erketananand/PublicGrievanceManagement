package com.scaler.grievance.utils;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceStatus;
import com.scaler.grievance.constants.GrievanceSubCategory;

public class QueryBuilderUtil {
    public static String buildSearchQuery(GrievanceCategory category, GrievanceSubCategory subCategory, GrievanceStatus status, GrievancePriority priority, String keyword) {
        String query = "SELECT * FROM grievances g WHERE 1=1";
        if(category != null) {
            query += " AND g.category = '" + category.name() + "'";
        }
        if(subCategory != null) {
            query += " AND g.sub_category = '" + subCategory.name() + "'";
        }
        if(status != null) {
            query += " AND g.status = '" + status.name() + "'";
        }
        if(priority != null) {
            query += " AND g.priority = '" + priority.name() + "'";
        }
        if(keyword != null) {
            query += " AND g.title LIKE '%" + keyword + "%' OR g.description LIKE '%" + keyword + "%'";
        }
        return query;
    }
}
