package com.scaler.user.models;

import java.util.List;

public class Permissions {
    private Long permissionId;
    private String permissionName;
    private String description;
    private List<RolePermissions> rolePermissions;
}
