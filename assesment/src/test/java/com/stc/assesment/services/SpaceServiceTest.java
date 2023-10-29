package com.stc.assesment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stc.assesment.dtos.SpaceRequestDTO;
import com.stc.assesment.exceptions.ResourceNotFoundException;
import com.stc.assesment.models.Item;
import com.stc.assesment.models.PermissionGroup;
import com.stc.assesment.repoistories.ItemRepository;
import com.stc.assesment.repoistories.PermissionsGroupRepository;
import com.stc.assesment.services.implementation.SpaceService;

public class SpaceServiceTest {

	@Mock
	private PermissionsGroupRepository permissionsGroupRepository;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private SpaceService spaceService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createNewSpace_ValidRequest_ExpectedItem() {
		// Arrange
		SpaceRequestDTO spaceRequestDTO = new SpaceRequestDTO();
		spaceRequestDTO.setSpaceName("TestSpace");
		UUID permissionGroupId = UUID.randomUUID();

		PermissionGroup permissionGroup = PermissionGroup.builder().groupName("Admin").id(UUID.randomUUID()).build();
		when(permissionsGroupRepository.findById(permissionGroupId)).thenReturn(Optional.of(permissionGroup));

		Item createdItem = Item.builder().type(Item.ItemType.SPACE).name(spaceRequestDTO.getSpaceName())
							   .permissionGroup(permissionGroup).build();
		when(itemRepository.save(any(Item.class))).thenReturn(createdItem);

		// Act
		Item result = spaceService.createNewSpace(spaceRequestDTO, permissionGroupId);

		// Assert
		assertNotNull(result);
		assertEquals(spaceRequestDTO.getSpaceName(), result.getName());
		assertEquals(Item.ItemType.SPACE, result.getType());
		assertEquals(permissionGroup, result.getPermissionGroup());
	}

	@Test
	void createNewSpace_InvalidPermissionGroup_ExpectedResourceNotFoundException() {
		// Arrange
		SpaceRequestDTO spaceRequestDTO = new SpaceRequestDTO();
		UUID permissionGroupId = UUID.randomUUID();

		when(permissionsGroupRepository.findById(permissionGroupId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> spaceService.createNewSpace(spaceRequestDTO, permissionGroupId));
	}

}
