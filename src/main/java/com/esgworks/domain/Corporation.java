package com.esgworks.domain;


import com.esgworks.dto.CorporationDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "corporation")
public class Corporation {
    @Id
    private String corpId;

    private String corpName;
    private String ceoName;
    private String industry;
    private String webpage;
    private Long revenue;
    private String address;
    private Integer employeeCnt;
    private String establishedDate;

    public CorporationDTO toDTO() {
        return CorporationDTO.builder()
                .corpId(corpId)
                .corpName(corpName)
                .ceoName(ceoName)
                .industry(industry)
                .webpage(webpage)
                .revenue(revenue)
                .address(address)
                .employeeCnt(employeeCnt)
                .establishedDate(establishedDate)
                .build();
    }
}
