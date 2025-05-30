package com.esgworks.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CorporationInfo {
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
