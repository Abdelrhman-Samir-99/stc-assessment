package com.stc.assesment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.implementation.PermissionGroupService;

public class PermissionGroupServiceTest {
	@Mock
	private PermissionsGroupRepository permissionsGroupRepository;

	@InjectMocks
	private PermissionGroupService permissionGroupService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createNewPermissionGroup_ValidRequest_ExpectedPermissionGroup() {
		// Arrange
		String expectedGroupName = "Admin";
		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();

		when(permissionsGroupRepository.save(any(PermissionGroup.class))).thenReturn(permissionGroup);

		// Act
		PermissionGroup createdGroup = permissionGroupService.createNewPermissionGroup(expectedGroupName);

		// Assert
		assertNotNull(createdGroup);
		assertEquals(expectedGroupName, createdGroup.getGroupName());
	}

}
