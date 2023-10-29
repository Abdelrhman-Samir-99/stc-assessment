package com.stc.assesment.facades.interfaces;

import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;

public interface ISpaceFacade {

	GenericResponseDTO createNewSpace(SpaceRequestDTO spaceRequestDTO);
}
