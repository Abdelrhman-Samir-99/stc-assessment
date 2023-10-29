package com.stc.assesment.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetadataDTO {
	private String fileName;
	private String parentNodeName;
	private UUID parentNodeId;
}
