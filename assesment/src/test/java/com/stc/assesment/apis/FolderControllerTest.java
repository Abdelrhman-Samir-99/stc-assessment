package com.stc.assesment.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stc.assesment.apis.implementaiton.FolderController;
import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.services.interfaces.IFolderService;

public class FolderControllerTest {
	@Mock
	private IFolderService folderService;

	@InjectMocks
	private FolderController folderController;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createFolder_ValidRequest_ExpectedResponseEntityWithCreatedStatus() throws AccessDeniedException {
		// Arrange
		FolderRequestDTO folderRequestDTO = new FolderRequestDTO();
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();
		GenericResponseDTO expectedResponse = GenericResponseDTO.builder().message("New folder created successfully!").code(HttpStatus.CREATED).build();

		when(folderService.createFolder(folderRequestDTO, permissionId, permissionGroupId)).thenReturn(expectedResponse);

		// Act
		ResponseEntity<GenericResponseDTO> response = folderController.createFolder(folderRequestDTO, permissionId, permissionGroupId);

		// Assert
		verify(folderService, times(1)).createFolder(folderRequestDTO, permissionId, permissionGroupId);
		assertEquals(expectedResponse, response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void createFolder_InvalidRequest_ExpectedException() throws AccessDeniedException {
		// Arrange
		FolderRequestDTO folderRequestDTO = new FolderRequestDTO();
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();

		when(folderService.createFolder(folderRequestDTO, permissionId, permissionGroupId)).thenThrow(AccessDeniedException.class);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> folderController.createFolder(folderRequestDTO, permissionId, permissionGroupId));
		verify(folderService, times(1)).createFolder(folderRequestDTO, permissionId, permissionGroupId);
	}
}
