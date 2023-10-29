package com.stc.assesment.services.implementation;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stc.assesment.dtos.FileMetadataDTO;
import com.stc.assesment.exceptions.ResourceNotFoundException;
import com.stc.assesment.models.Files;
import com.stc.assesment.models.Item;
import com.stc.assesment.repoistories.FileRepository;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.services.interfaces.IFileService;
import com.stc.assesment.services.interfaces.IPermissionService;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

	private final FileRepository fileRepository;
	private final ItemRepository itemRepository;
	private final IPermissionService permissionService;

	@Override
	public void saveFile(UUID permissionId, UUID permissionGroupId, UUID parentNodeId, MultipartFile file) throws IOException {

		Boolean havePermissionToEdit = permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);
		if(!havePermissionToEdit.booleanValue()) {
			throw new AccessDeniedException(ConstantErrorMessages.USER_DOES_NOT_HAVE_PERMISSION);
		}


		Item theParentNode = itemRepository.findById(parentNodeId)
										   .orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.ITEM_RECORD_NOT_FOUND_WITH_ID + permissionGroupId));

		fileRepository.save(
				Files.builder().binaryData(file.getBytes())
					 .fileName(file.getOriginalFilename()).item(theParentNode)
					 .build()
		);
	}

	@Override
	public ResponseEntity<byte[]> downloadFile(UUID permissionId, UUID fileId) throws AccessDeniedException {
		Files file = getFile(fileId, permissionId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", file.getFileName());


		return new ResponseEntity<>(file.getBinaryData(), headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<FileMetadataDTO> getFileMetadata(UUID fileId, UUID permissionId) throws AccessDeniedException {
		Files file = getFile(fileId, permissionId);

		return ResponseEntity.ok(
				FileMetadataDTO.builder().fileName(file.getFileName())
						.parentNodeName(file.getItem().getName())
						.parentNodeId(file.getItem().getId()).build()
		);
	}

	private Files getFile(UUID fileId, UUID permissionId) throws AccessDeniedException {
		Files file = fileRepository.findById(fileId)
								   .orElseThrow(() -> new ResourceNotFoundException(ConstantErrorMessages.FILE_RECORD_NOT_FOUND_WITH_ID + fileId));

		UUID permissionGroupId = file.getItem().getPermissionGroup().getId();

		Boolean havePermissionToView = permissionService.permissionHaveViewAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);
		Boolean havePermissionToEdit = permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);

		if(!(havePermissionToView || havePermissionToEdit)) {
			throw new AccessDeniedException(ConstantErrorMessages.USER_DOES_NOT_HAVE_PERMISSION);
		}
		return file;
	}
}
