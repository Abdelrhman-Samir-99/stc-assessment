package com.stc.assesment.services.implementation;

import org.springframework.stereotype.Service;

import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.interfaces.IPermissionGroupService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionGroupService implements IPermissionGroupService {

	private final PermissionsGroupRepository permissionsGroupRepository;

	@Override
	public PermissionGroup createNewPermissionGroup(String groupName) {
		return permissionsGroupRepository.save(
				PermissionGroup.builder().groupName(groupName).build()
		);
	}
}