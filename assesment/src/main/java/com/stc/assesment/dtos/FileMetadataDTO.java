package com.stc.assesment.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FileMetadataDTO {
	private String fileName;
	private String parentNodeName;
	private UUID parentNodeId;
}
