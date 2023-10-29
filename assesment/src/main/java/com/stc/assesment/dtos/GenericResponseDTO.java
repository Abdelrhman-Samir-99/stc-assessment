package com.stc.assesment.dtos;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponseDTO {
	private HttpStatus code;
	private String message;
}
