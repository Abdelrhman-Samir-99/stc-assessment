package com.stc.assesment.services.interfaces;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import com.stc.assesment.dtos.FolderRequestDTO;
import com.stc.assesment.dtos.GenericResponseDTO;

public interface IFolderService {

	GenericResponseDTO createFolder(FolderRequestDTO folderRequestDTO, UUID permissionId, UUID permissionGroupId) throws AccessDeniedException;
}
