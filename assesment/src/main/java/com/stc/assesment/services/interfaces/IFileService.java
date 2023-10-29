package com.stc.assesment.services.interfaces;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.dtos.FileMetadataDTO;

public interface IFileService {
	void saveFile(UUID permissionId, UUID permissionGroupId, UUID parentNodeId, MultipartFile file) throws IOException;

	ResponseEntity<byte[]> downloadFile(UUID permissionId, UUID fileId) throws AccessDeniedException;

	ResponseEntity<FileMetadataDTO> getFileMetadata(UUID fileId, UUID permissionId) throws AccessDeniedException;
}
