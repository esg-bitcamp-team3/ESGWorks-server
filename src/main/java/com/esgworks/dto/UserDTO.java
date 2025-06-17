package com.esgworks.dto;

import com.esgworks.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
  private String id;
  private String name;
//  private String password;
  private String email;
  private String phoneNumber;
  private String corpId;
}
