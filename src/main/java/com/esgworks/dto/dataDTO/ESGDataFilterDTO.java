package com.esgworks.dto.dataDTO;

import lombok.Data;

@Data
public class ESGDataFilterDTO {
  private String categoryId;
  private String year;
  private String corpId;
  private String value;
}
