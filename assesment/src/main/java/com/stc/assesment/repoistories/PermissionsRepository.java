package com.stc.assesment.repoistories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stc.assesment.models.Permissions;

public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
	Optional<Permissions> findByIdAndGroupId(UUID id, UUID permissionGroupId);
}
