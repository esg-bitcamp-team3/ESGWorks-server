package com.esgworks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategorizedESGDataListDTO {
    private CategoryDetailDTO categoryDetailDTO;
    private List<ESGNumberDTO> esgNumberDTOList;
}
