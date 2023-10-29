package com.stc.assesment.facades.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.facades.interfaces.ISpaceFacade;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.models.Permissions;
import com.stc.assesment.services.interfaces.IPermissionGroupService;
import com.stc.assesment.services.interfaces.IPermissionService;
import com.stc.assesment.services.interfaces.ISpaceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SpaceFacade implements ISpaceFacade {

	private final ISpaceService spaceService;
	private final IPermissionGroupService permissionGroupService;
	private final IPermissionService permissionService;

	@Override
	@Transactional
	public GenericResponseDTO createNewSpace(SpaceRequestDTO spaceRequestDTO) {
		PermissionGroup permissionGroup = permissionGroupService.createNewPermissionGroup(spaceRequestDTO.getGroupName());

		permissionService.createNewPermission(spaceRequestDTO.getEditAccessUserEmail(), Permissions.AccessType.EDIT, permissionGroup.getId());
		permissionService.createNewPermission(spaceRequestDTO.getViewAccessUserEmail(), Permissions.AccessType.VIEW, permissionGroup.getId());

		spaceService.createNewSpace(spaceRequestDTO, permissionGroup.getId());

		return GenericResponseDTO.builder().message("New space created successfully!").code(HttpStatus.CREATED).build();
	}
}
