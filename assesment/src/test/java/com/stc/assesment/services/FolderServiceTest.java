package com.stc.assesment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.implementation.FolderService;
import com.stc.assesment.services.interfaces.IPermissionService;

public class FolderServiceTest {
	
	@Mock
	private ItemRepository itemRepository;

	@Mock
	private PermissionsGroupRepository permissionsGroupRepository;

	@Mock
	private IPermissionService permissionService;

	@InjectMocks
	private FolderService folderService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createFolder_ValidPermissionAndPermissionGroup_ExpectedGenericResponseDTO() throws AccessDeniedException {
		// Arrange
		FolderRequestDTO folderRequestDTO = new FolderRequestDTO();
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();

		when(permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId))
				.thenReturn(true);

		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		when(permissionsGroupRepository.findById(permissionGroupId)).thenReturn(Optional.of(permissionGroup));
		when(itemRepository.save(any(Item.class))).thenReturn(new Item());


		// Act
		GenericResponseDTO response = folderService.createFolder(folderRequestDTO, permissionId, permissionGroupId);

		// Assert
		assertNotNull(response);
		assertEquals("New folder created successfully!", response.getMessage());
		assertEquals(HttpStatus.CREATED, response.getCode());
	}

	@Test
	void createFolder_InvalidPermission_ExpectedAccessDeniedException() {
		// Arrange
		FolderRequestDTO folderRequestDTO = new FolderRequestDTO();
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();

		when(permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId))
				.thenReturn(false);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> folderService.createFolder(folderRequestDTO, permissionId, permissionGroupId));
	}
}
