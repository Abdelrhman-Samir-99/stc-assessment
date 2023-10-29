package com.stc.assesment.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stc.assesment.apis.implementaiton.SpaceController;
import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.facades.interfaces.ISpaceFacade;
import com.stc.assesment.utils.ConstantErrorMessages;

public class SpaceControllerTest {
	@Mock
	private ISpaceFacade spaceFacade;

	@InjectMocks
	private SpaceController spaceController;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createSpace_ValidRequest_ExpectedResponseEntityWithCreatedStatus() {
		// Arrange
		SpaceRequestDTO spaceRequestDTO = new SpaceRequestDTO();
		spaceRequestDTO.setSpaceName("TestSpace");
		GenericResponseDTO expectedResponse =  GenericResponseDTO.builder().message("New space created successfully!").code(HttpStatus.CREATED).build();

		when(spaceFacade.createNewSpace(spaceRequestDTO)).thenReturn(expectedResponse);

		// Act
		ResponseEntity<GenericResponseDTO> response = spaceController.createSpace(spaceRequestDTO);

		// Assert
		verify(spaceFacade, times(1)).createNewSpace(spaceRequestDTO);
		assertEquals(expectedResponse, response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void createSpace_InvalidRequest_ExpectedException() {
		// Arrange
		SpaceRequestDTO spaceRequestDTO = new SpaceRequestDTO();
		IllegalArgumentException expectedException = new IllegalArgumentException(ConstantErrorMessages.SPACE_DTO_MISSING_INFO_ERROR);

		// Act and Assert
		IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> spaceController.createSpace(spaceRequestDTO));
		verify(spaceFacade, never()).createNewSpace(spaceRequestDTO);
		assertEquals(expectedException.getMessage(), actualException.getMessage());
	}
}
