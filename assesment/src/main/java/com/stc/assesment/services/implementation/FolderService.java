package com.stc.assesment.services.implementation;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.exceptions.ResourceNotFoundException;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.interfaces.IFolderService;
import com.stc.assesment.services.interfaces.IPermissionService;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService implements IFolderService {

	private final ItemRepository itemRepository;
	private final PermissionsGroupRepository permissionsGroupRepository;
	private final IPermissionService permissionService;

	@Override
	public GenericResponseDTO createFolder(FolderRequestDTO folderRequestDTO, UUID permissionId, UUID permissionGroupId) throws AccessDeniedException {
		Boolean havePermissionToEdit = permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);
		if(!havePermissionToEdit.booleanValue()) {
			throw new AccessDeniedException(ConstantErrorMessages.USER_DOES_NOT_HAVE_PERMISSION);
		}

		PermissionGroup permissionGroup = permissionsGroupRepository.findById(permissionGroupId)
																	.orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.PERMISSION_GROUP_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));
		itemRepository.save(
				Item.builder().name(folderRequestDTO.getFolderName())
					.type(Item.ItemType.FOLDER).permissionGroup(permissionGroup).build()
		);

		return GenericResponseDTO.builder().message("New folder created successfully!").code(HttpStatus.CREATED).build();
	}

}
