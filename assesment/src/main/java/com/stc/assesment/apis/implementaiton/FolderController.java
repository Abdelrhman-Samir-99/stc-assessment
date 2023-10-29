package com.stc.assesment.apis.implementaiton;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.stc.assesment.apis.interfaces.IFolderApi;
import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.services.interfaces.IFolderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FolderController implements IFolderApi {

	private final IFolderService folderService;

	@Override
	public ResponseEntity<GenericResponseDTO> createFolder(FolderRequestDTO folderRequestDTO, UUID permissionId, UUID permissionGroupId)
			throws AccessDeniedException {
		return new ResponseEntity<>(folderService.createFolder(folderRequestDTO, permissionId, permissionGroupId), HttpStatus.CREATED);
	}
}
