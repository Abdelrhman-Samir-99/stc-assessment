package com.stc.assesment.services.implementation;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.exceptions.ResourceNotFoundException;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.interfaces.ISpaceService;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpaceService implements ISpaceService {

	private final PermissionsGroupRepository permissionsGroupRepository;
	private final ItemRepository itemRepository;

	@Override
	public Item createNewSpace(SpaceRequestDTO spaceRequestDTO, UUID permissionGroupId) {
		PermissionGroup permissionGroup = permissionsGroupRepository.findById(permissionGroupId)
																	.orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.PERMISSION_GROUP_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));

		return itemRepository.save(
				Item.builder().type(Item.ItemType.SPACE).name(spaceRequestDTO.getSpaceName())
						.permissionGroup(permissionGroup).build()
		);
	}
}