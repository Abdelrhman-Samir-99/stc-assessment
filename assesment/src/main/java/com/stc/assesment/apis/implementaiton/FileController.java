package com.stc.assesment.apis.implementaiton;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.apis.interfaces.IFileApi;
import com.stc.assesment.dtos.FileMetadataDTO;
import com.stc.assesment.services.interfaces.IFileService;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController implements IFileApi {

	private final IFileService fileService;

	@Override
	public ResponseEntity<String> uploadFile(UUID permissionId, UUID permissionGroupId, UUID parentNodeId, MultipartFile file)
			throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(ConstantErrorMessages.THE_FILE_IS_EMPTY);
		}

		fileService.saveFile(permissionId, permissionGroupId, parentNodeId, file);
		return ResponseEntity.ok(ConstantErrorMessages.FILE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public ResponseEntity<byte[]> downloadFile(UUID permissionId, UUID fileId) throws AccessDeniedException {
		return fileService.downloadFile(permissionId, fileId);
	}

	@Override
	public ResponseEntity<FileMetadataDTO> getFileMetadata(UUID fileId, UUID permissionId) throws AccessDeniedException {
		return fileService.getFileMetadata(fileId, permissionId);
	}
}
