package com.esgworks.dto;

import com.esgworks.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class UserDTO {
  private String id;
  private String name;
  private String password;
  private String email;
  private String phoneNumber;
  private String corpId;


}
