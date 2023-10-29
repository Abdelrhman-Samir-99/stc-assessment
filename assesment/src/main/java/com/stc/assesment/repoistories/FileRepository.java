package com.stc.assesment.repoistories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stc.assesment.models.Files;

public interface FileRepository extends JpaRepository<Files, UUID> {

}
