package com.stc.assesment.repoistories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stc.assesment.models.PermissionGroup;

public interface PermissionsGroupRepository extends JpaRepository<PermissionGroup, UUID> {

}
