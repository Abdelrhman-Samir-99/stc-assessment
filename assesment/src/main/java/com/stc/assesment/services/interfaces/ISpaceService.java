package com.stc.assesment.services.interfaces;

import java.util.UUID;

import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.models.Item;

public interface ISpaceService {
	Item createNewSpace(SpaceRequestDTO spaceRequestDTO, UUID permissionGroupId);
}
