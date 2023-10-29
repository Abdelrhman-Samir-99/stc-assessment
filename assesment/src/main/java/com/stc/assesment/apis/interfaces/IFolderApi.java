package com.stc.assesment.apis.interfaces;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;

@RequestMapping("api/v1/folder")
public interface IFolderApi {
	@PostMapping("/create")
	ResponseEntity<GenericResponseDTO> createFolder(@RequestBody FolderRequestDTO folderRequestDTO, @RequestParam UUID permissionId, @RequestParam UUID permissionGroupId)
			throws AccessDeniedException;
}
