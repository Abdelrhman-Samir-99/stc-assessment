package com.stc.assesment.apis.interfaces;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.dtos.FileMetadataDTO;

@RequestMapping("api/v1/file")
public interface IFileApi {

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<String> uploadFile(@RequestParam("permissionId") UUID permissionId, @RequestParam("permissionGroupId") UUID permissionGroupId,
			@RequestParam("parentNodeId") UUID parentNodeId, @RequestParam("file") MultipartFile file) throws IOException;

	@GetMapping("/download")
	ResponseEntity<byte[]> downloadFile(@RequestParam UUID permissionId, @RequestParam UUID fileId) throws AccessDeniedException;

	@GetMapping("/{fileId}/metadata")
	ResponseEntity<FileMetadataDTO> getFileMetadata(@PathVariable UUID fileId, @RequestParam UUID permissionId)
			throws AccessDeniedException;
}