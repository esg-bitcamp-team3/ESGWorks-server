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
    private String revenue;
    private String address;
    private String employeeCnt;
    private String establishedDate;
}
