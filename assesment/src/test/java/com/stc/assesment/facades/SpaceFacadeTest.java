package com.stc.assesment.facades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.stc.assesment.dtos.GenericResponseDTO;
import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.facades.implementation.SpaceFacade;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.models.Permissions;
import com.stc.assesment.services.interfaces.IPermissionGroupService;
import com.stc.assesment.services.interfaces.IPermissionService;
import com.stc.assesment.services.interfaces.ISpaceService;

public class SpaceFacadeTest {
	@Mock
	private ISpaceService spaceService;

	@Mock
	private IPermissionGroupService permissionGroupService;

	@Mock
	private IPermissionService permissionService;

	@InjectMocks
	private SpaceFacade spaceFacade;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createNewSpace_ValidRequest_ExpectedGenericResponseDTO() {
		// Arrange
		SpaceRequestDTO spaceRequestDTO = new SpaceRequestDTO();
		spaceRequestDTO.setGroupName("TestGroup");
		spaceRequestDTO.setEditAccessUserEmail("edit@example.com");
		spaceRequestDTO.setViewAccessUserEmail("view@example.com");

		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		when(permissionGroupService.createNewPermissionGroup(spaceRequestDTO.getGroupName())).thenReturn(permissionGroup);

		when(permissionService.createNewPermission(spaceRequestDTO.getEditAccessUserEmail(), Permissions.AccessType.EDIT, permissionGroup.getId()))
				.thenReturn(new Permissions());

		when(permissionService.createNewPermission(spaceRequestDTO.getViewAccessUserEmail(), Permissions.AccessType.VIEW, permissionGroup.getId()))
				.thenReturn(new Permissions());

		// Act
		GenericResponseDTO response = spaceFacade.createNewSpace(spaceRequestDTO);

		// Assert
		verify(permissionGroupService, times(1)).createNewPermissionGroup(spaceRequestDTO.getGroupName());
		verify(permissionService, times(1)).createNewPermission(spaceRequestDTO.getEditAccessUserEmail(), Permissions.AccessType.EDIT, permissionGroup.getId());
		verify(permissionService, times(1)).createNewPermission(spaceRequestDTO.getViewAccessUserEmail(), Permissions.AccessType.VIEW, permissionGroup.getId());
		verify(spaceService, times(1)).createNewSpace(spaceRequestDTO, permissionGroup.getId());
		assertEquals("New space created successfully!", response.getMessage());
		assertEquals(HttpStatus.CREATED, response.getCode());
	}
}
