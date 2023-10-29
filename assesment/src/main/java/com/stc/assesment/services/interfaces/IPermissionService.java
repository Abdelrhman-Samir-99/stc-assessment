package com.stc.assesment.services.interfaces;

import java.util.UUID;

import com.stc.assesment.models.Permissions;

public interface IPermissionService {
	Permissions createNewPermission(String userEmail, Permissions.AccessType accessType, UUID permissionGroupId);

	Boolean permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(UUID permissionId, UUID permissionGroupId);
	Boolean permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(UUID permissionId, UUID permissionGroupId);
}
