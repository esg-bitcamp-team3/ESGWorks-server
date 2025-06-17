package com.esgworks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRes {
  String token;
}
