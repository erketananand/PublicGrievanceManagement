package com.scaler.user.models;

import java.util.List;

public class Role {
    private Long roleId;
    private RoleType roleType;
    private List<UserRoles> userRoles;
    private List<RolePermissions> rolePermissions;
}
