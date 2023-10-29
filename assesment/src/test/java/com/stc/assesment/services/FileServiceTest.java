package com.stc.assesment.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.dtos.FileMetadataDTO;
import com.stc.assesment.models.Files;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.FileRepository;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.services.implementation.FileService;
import com.stc.assesment.services.interfaces.IPermissionService;

public class FileServiceTest {
	@Mock
	private FileRepository fileRepository;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private IPermissionService permissionService;

	@InjectMocks
	private FileService fileService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void saveFile_ValidPermissionAndParentNode_ExpectedNoException() throws IOException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();
		UUID parentNodeId = UUID.randomUUID();
		MultipartFile file = mock(MultipartFile.class);

		when(permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId))
				.thenReturn(true);

		Item theParentNode = new Item();
		when(itemRepository.findById(parentNodeId)).thenReturn(Optional.of(theParentNode));

		when(fileRepository.save(any(Files.class))).thenReturn(new Files());

		// Act
		assertDoesNotThrow(() -> fileService.saveFile(permissionId, permissionGroupId, parentNodeId, file));
	}

	@Test
	void saveFile_InvalidPermission_ExpectedAccessDeniedException() throws IOException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();
		UUID parentNodeId = UUID.randomUUID();
		MultipartFile file = mock(MultipartFile.class);

		when(permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId))
				.thenReturn(false);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> fileService.saveFile(permissionId, permissionGroupId, parentNodeId, file));
	}

	@Test
	void downloadFile_ValidPermission_ExpectedResponseEntityWithFile() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		Item createdItem = Item.builder().type(Item.ItemType.SPACE).name("backend")
							   .permissionGroup(permissionGroup).build();
		Files file = Files.builder().id(fileId).item(createdItem).build();

		when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
		when(permissionService.permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(permissionId, file.getItem().getPermissionGroup().getId()))
				.thenReturn(true);

		// Act
		ResponseEntity<byte[]> response = fileService.downloadFile(permissionId, fileId);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void downloadFile_InvalidPermission_ExpectedAccessDeniedException() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		Item createdItem = Item.builder().type(Item.ItemType.SPACE).name("backend")
							   .permissionGroup(permissionGroup).build();
		Files file = Files.builder().id(fileId).item(createdItem).build();


		when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
		when(permissionService.permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(permissionId, file.getItem().getPermissionGroup().getId()))
				.thenReturn(false);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> fileService.downloadFile(permissionId, fileId));
	}

	@Test
	void getFileMetadata_ValidPermission_ExpectedResponseEntityWithFileMetadata() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		Item createdItem = Item.builder().type(Item.ItemType.SPACE).name("backend")
							   .permissionGroup(permissionGroup).build();
		Files file = Files.builder().id(fileId).item(createdItem).build();

		when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
		when(permissionService.permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(permissionId, file.getItem().getPermissionGroup().getId()))
				.thenReturn(true);

		// Act
		ResponseEntity<FileMetadataDTO> response = fileService.getFileMetadata(fileId, permissionId);

		// Assert
		assertNotNull(response);
	}

	@Test
	void getFileMetadata_InvalidPermission_ExpectedAccessDeniedException() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		Item createdItem = Item.builder().type(Item.ItemType.SPACE).name("backend")
							   .permissionGroup(permissionGroup).build();
		Files file = Files.builder().id(fileId).item(createdItem).build();

		when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
		when(permissionService.permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(permissionId, file.getItem().getPermissionGroup().getId()))
				.thenReturn(false);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> fileService.getFileMetadata(fileId, permissionId));
	}
}
