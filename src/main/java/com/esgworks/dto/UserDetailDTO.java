package com.esgworks.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class UserDetailDTO {
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private CorporationDTO corporation;

}
