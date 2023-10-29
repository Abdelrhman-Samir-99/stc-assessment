package com.stc.assesment.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.apis.implementaiton.FileController;
import com.stc.assesment.dtos.FileMetadataDTO;
import com.stc.assesment.services.interfaces.IFileService;
import com.stc.assesment.utils.ConstantErrorMessages;

public class FileControllerTest {

	@Mock
	private IFileService fileService;

	@InjectMocks
	private FileController fileController;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void uploadFile_NonEmptyFile_ExpectedSuccess() throws IOException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();
		UUID parentNodeId = UUID.randomUUID();
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(false);

		// Act
		ResponseEntity<String> response = fileController.uploadFile(permissionId, permissionGroupId, parentNodeId, file);

		// Assert
		verify(fileService, times(1)).saveFile(permissionId, permissionGroupId, parentNodeId, file);
		assertEquals(ResponseEntity.ok(ConstantErrorMessages.FILE_UPDATED_SUCCESSFULLY), response);
	}

	@Test
	void uploadFile_EmptyFile_ExpectedBadRequest() throws IOException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();
		UUID parentNodeId = UUID.randomUUID();
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);

		// Act
		ResponseEntity<String> response = fileController.uploadFile(permissionId, permissionGroupId, parentNodeId, file);

		// Assert
		assertEquals(ResponseEntity.badRequest().body(ConstantErrorMessages.THE_FILE_IS_EMPTY), response);
		verify(fileService, never()).saveFile(any(UUID.class), any(UUID.class), any(UUID.class), any(MultipartFile.class));
	}

	@Test
	void downloadFile_ValidPermissionAndFile_ExpectedResponseEntityWithFile() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok(new byte[]{1, 2, 3});

		when(fileService.downloadFile(permissionId, fileId)).thenReturn(expectedResponse);

		// Act
		ResponseEntity<byte[]> response = fileController.downloadFile(permissionId, fileId);

		// Assert
		verify(fileService, times(1)).downloadFile(permissionId, fileId);
		assertEquals(expectedResponse, response);
	}

	@Test
	void downloadFile_InvalidPermissionOrFile_ExpectedException() throws AccessDeniedException {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();

		when(fileService.downloadFile(permissionId, fileId)).thenThrow(AccessDeniedException.class);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> fileController.downloadFile(permissionId, fileId));
		verify(fileService, times(1)).downloadFile(permissionId, fileId);
	}

	@Test
	void getFileMetadata_ValidFileIdAndPermission_ExpectedResponseEntityWithMetadata() throws AccessDeniedException {
		// Arrange
		UUID fileId = UUID.randomUUID();
		UUID permissionId = UUID.randomUUID();
		FileMetadataDTO expectedMetadata = new FileMetadataDTO();

		when(fileService.getFileMetadata(fileId, permissionId)).thenReturn(new ResponseEntity<>(expectedMetadata,
				HttpStatusCode.valueOf(200)));

		// Act
		ResponseEntity<FileMetadataDTO> response = fileController.getFileMetadata(fileId, permissionId);

		// Assert
		verify(fileService, times(1)).getFileMetadata(fileId, permissionId);
		assertEquals(expectedMetadata, response.getBody());
	}

	@Test
	void getFileMetadata_InvalidFileIdOrPermission_ExpectedException() throws AccessDeniedException {
		// Arrange
		UUID fileId = UUID.randomUUID();
		UUID permissionId = UUID.randomUUID();

		when(fileService.getFileMetadata(fileId, permissionId)).thenThrow(AccessDeniedException.class);

		// Act and Assert
		assertThrows(AccessDeniedException.class, () -> fileController.getFileMetadata(fileId, permissionId));
		verify(fileService, times(1)).getFileMetadata(fileId, permissionId);
	}
}
