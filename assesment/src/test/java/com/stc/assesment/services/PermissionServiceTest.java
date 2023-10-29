package com.stc.assesment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.models.Permissions;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.repoistories.PermissionsRepository;
import com.stc.assesment.services.implementation.PermissionService;

public class PermissionServiceTest {

	@Mock
	private PermissionsRepository permissionsRepository;

	@Mock
	private PermissionsGroupRepository permissionsGroupRepository;

	@InjectMocks
	private PermissionService permissionService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createNewPermission_ValidRequest_ExpectedPermission() {
		// Arrange
		String userEmail = "test@example.com";
		Permissions.AccessType accessType = Permissions.AccessType.EDIT;
		UUID permissionGroupId = UUID.randomUUID();

		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		when(permissionsGroupRepository.findById(permissionGroupId)).thenReturn(Optional.of(permissionGroup));

		Permissions createdPermission = Permissions.builder().permissionLevel(Permissions.AccessType.EDIT)
												   .group(permissionGroup).userEmail(userEmail).build();
		when(permissionsRepository.save(any(Permissions.class))).thenReturn(createdPermission);

		// Act
		Permissions result = permissionService.createNewPermission(userEmail, accessType, permissionGroupId);

		// Assert
		assertNotNull(result);
		assertEquals(userEmail, result.getUserEmail());
		assertEquals(accessType, result.getPermissionLevel());
		assertEquals(permissionGroup, result.getGroup());
	}

	@Test
	void permissionHaveEditAccessAndBelongsToTheSamePermissionGroup_ValidPermission_ExpectedTrue() {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();

		Permissions permission =  Permissions.builder().permissionLevel(Permissions.AccessType.EDIT).build();
		when(permissionsRepository.findByIdAndGroupId(permissionId, permissionGroupId)).thenReturn(Optional.of(permission));
		permission.setPermissionLevel(Permissions.AccessType.EDIT);

		// Act
		Boolean result = permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);

		// Assert
		assertTrue(result);
	}

	@Test
	void permissionHaveEditAccessAndBelongsToTheSamePermissionGroup_InvalidPermission_ExpectedFalse() {
		// Arrange
		UUID permissionId = UUID.randomUUID();
		UUID permissionGroupId = UUID.randomUUID();

		Permissions permission = Permissions.builder().permissionLevel(Permissions.AccessType.VIEW).build();
		when(permissionsRepository.findByIdAndGroupId(permissionId, permissionGroupId)).thenReturn(Optional.of(permission));


		// Act
		Boolean result = permissionService.permissionHaveEditAccessAndBelongsToTheSamePermissionGroup(permissionId, permissionGroupId);

		// Assert
		assertFalse(result);
	}

}
