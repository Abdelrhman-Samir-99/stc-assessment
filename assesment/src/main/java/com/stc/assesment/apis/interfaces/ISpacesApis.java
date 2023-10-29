package com.stc.assesment.apis.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;


@RequestMapping("api/v1/space")
public interface ISpacesApis {
	@PostMapping("/create")
	ResponseEntity<GenericResponseDTO> createSpace(@RequestBody SpaceRequestDTO spaceRequestDTO);
}
