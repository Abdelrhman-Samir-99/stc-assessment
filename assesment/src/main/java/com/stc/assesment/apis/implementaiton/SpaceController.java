package com.stc.assesment.apis.implementaiton;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.stc.assesment.apis.interfaces.ISpacesApis;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.facades.interfaces.ISpaceFacade;
import com.stc.assesment.utils.ConstantErrorMessages;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SpaceController implements ISpacesApis {

    private final ISpaceFacade spaceFacade;

    @Override
    public ResponseEntity<GenericResponseDTO> createSpace(SpaceRequestDTO spaceRequestDTO) {
        if(spaceRequestDTO == null || !StringUtils.hasText(spaceRequestDTO.getSpaceName())) {
            throw new IllegalArgumentException(ConstantErrorMessages.SPACE_DTO_MISSING_INFO_ERROR);
        }

        return new ResponseEntity<>(spaceFacade.createNewSpace(spaceRequestDTO), HttpStatus.CREATED);
    }
}