package com.stc.assesment.dtos;

import lombok.Data;

@Data
public class SpaceRequestDTO {
    private String spaceName;
    private String groupName;
    private String viewAccessUserEmail;
    private String editAccessUserEmail;

}