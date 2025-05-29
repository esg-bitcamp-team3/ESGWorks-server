package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporationDTO {
    private String corpId;
    private String corpName;
    private String ceoName;
    private String industry;
    private String webpage;
    private Long revenue;
    private String address;
    private Integer employeeCnt;
    private String establishedDate;
}
