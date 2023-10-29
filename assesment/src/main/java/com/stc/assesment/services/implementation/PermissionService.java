package com.stc.assesment.services.implementation;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.stc.assesment.exceptions.ResourceNotFoundException;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.models.Permissions;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.repoistories.PermissionsRepository;
import com.stc.assesment.services.interfaces.IPermissionService;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PermissionService implements IPermissionService {

	private final PermissionsRepository permissionsRepository;
	private final PermissionsGroupRepository permissionsGroupRepository;


	@Override
	public Permissions createNewPermission(String userEmail, Permissions.AccessType accessType, UUID permissionGroupId) {
		PermissionGroup permissionGroup = permissionsGroupRepository.findById(permissionGroupId)
																	.orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.PERMISSION_GROUP_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));
		return permissionsRepository.save(
				Permissions.builder().permissionLevel(accessType)
						   .userEmail(userEmail).group(permissionGroup).build()
		);
	}



	public Boolean permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(UUID permissionId, UUID permissionGroupId) {
		Permissions permission = permissionsRepository.findByIdAndGroupId(permissionId, permissionGroupId)
													  .orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.PERMISSION_GROUP_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));

		return permission.getPermissionLevel().equals(Permissions.AccessType.EDIT);
	}

	@Override
	public Boolean permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(UUID permissionId, UUID permissionGroupId) {
		Permissions permission = permissionsRepository.findByIdAndGroupId(permissionId, permissionGroupId)
													  .orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.PERMISSION_GROUP_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));

		return permission.getPermissionLevel().equals(Permissions.AccessType.VIEW);
	}
}
