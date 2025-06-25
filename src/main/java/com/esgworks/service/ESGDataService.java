package com.esgworks.service;

import com.esgworks.domain.Criterion;
import com.esgworks.domain.ESGData;
import com.esgworks.domain.User;
import com.esgworks.dto.*;
import com.esgworks.dto.dataDTO.ESGDataFilterDTO;
import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.InvalidRequestException;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.ESGDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ESGDataService {

    private final ESGDataRepository esgDataRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UnitService unitService;

    public ESGDataDTO getESGDataById(String esgDataId) {
        ESGData esgData = esgDataRepository.findByEsgDataId(esgDataId).orElseThrow(() -> new RuntimeException("없는 ESG데이터입니다."));
        return esgData.toDTO();
    }

    // 전체 ESG 데이터 조회
    public List<ESGDataDTO> getAllESGData() {
        return esgDataRepository.findAll().stream().map(ESGData::toDTO).toList();
    }

    // 카테고리 ID로 조회
    public List<ESGDataDTO> getESGDataByCategoryId(String categoryId) {
        return esgDataRepository.findByCategoryId(categoryId).stream().map(ESGData::toDTO).toList();
    }

    // 기업 ID로 조회
    public List<ESGDataDTO> getESGDataByCorpId(String corpId) {
        return esgDataRepository.findByCorpId(corpId).stream().map(ESGData::toDTO).toList();
    }

    // 기업 ID + 연도로 단일 조회
    public List<ESGDataDTO> getESGDataByCorpIdAndYear(String corpId, String year) {
        return esgDataRepository.findAllByCorpIdAndYear(corpId, year).stream().map(ESGData::toDTO).toList();
    }

    // ESG 데이터 생성
    public ESGDataDTO createESGData(ESGDataDTO dto, String userId) {
        if (dto.getCategoryId() == null || dto.getCorpId() == null || dto.getYear() == null) {
            throw new IllegalArgumentException("CategoryId, CorpId, Year는 필수 값입니다.");
        }

        Optional<ESGData> optional = esgDataRepository.findByCorpIdAndYear(dto.getCorpId(), dto.getYear());
        if (optional.isPresent()) {
            throw new DuplicateException("해당 ESG 데이터가 이미 존재합니다.");
        }

        ESGData esgData = ESGData.builder()
                .categoryId(dto.getCategoryId())
                .corpId(dto.getCorpId())
                .year(dto.getYear())
                .value(dto.getValue())
                .createdAt(dto.getCreatedAt())
                .createdBy(userId)
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(userId)
                .build();

        esgDataRepository.save(esgData);
        return esgData.toDTO(); // 수정: 저장된 결과 반환
    }

    public ESGDataDTO updateESGData(String corpId, String year, ESGDataDTO dto, String userId) {
        ESGData existing = esgDataRepository.findByCorpIdAndYear(corpId, year)
                .orElseThrow(() -> new NotFoundException("해당 ESG 데이터가 존재하지 않습니다."));

        ESGData updated = ESGData.builder()
                .categoryId(dto.getCategoryId())
                .corpId(corpId)
                .year(year)
                .value(dto.getValue())
                .createdAt(existing.getCreatedAt())
                .createdBy(existing.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(userId)
                .build();

        return esgDataRepository.save(updated).toDTO();
    }

    // ESG 데이터 삭제
    public void deleteESGData(String corpId, String year) {
        ESGData data = esgDataRepository.findByCorpIdAndYear(corpId, year)
                .orElseThrow(() -> new NotFoundException("해당 ESG 데이터가 존재하지 않습니다."));
        esgDataRepository.delete(data);
    }

    public CategorizedESGDataListDTO getESGDataByCategoryId(String categoryId, String userId) {
        CategoryDetailDTO categoryDetailDTO = categoryService.getCategoryById(categoryId);
        if (categoryDetailDTO.getUnit().getType().equals("String")) {
            throw new IllegalArgumentException("적절하지 않음");
        }
        String corpId = userService.findById(userId).getCorpId();

        List<ESGData> esgDataList = esgDataRepository.findByCategoryIdAndCorpId(categoryId, corpId);

        List<ESGNumberDTO> esgNumberDTOList = esgDataList.stream()
                .map(esg -> {
                    String rawValue = esg.getValue();
                    double parsedValue;
                    String esgDataId = esg.getEsgDataId();


                    try {
                        parsedValue = Double.parseDouble(rawValue);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("value가 숫자가 아닙니다: " + rawValue);
                    }

<<<<<<< HEAD
                    return esg.toNumberDTO(parsedValue);
=======
                    return ESGNumberDTO.builder()
                            .categoryId(categoryId)
                            .corpId(corpId)
                            .esgDataId(esgDataId)
                            .year(esg.getYear())
                            .value(parsedValue)
                            .build();
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
                })
                .toList();


        CategorizedESGDataListDTO result = CategorizedESGDataListDTO.builder()
                .categoryDetailDTO(categoryDetailDTO)
                .esgNumberDTOList(esgNumberDTOList)
                .build();
        return result;
    }


    public ESGDataDTO getByCorpIdAndYearAndCategoryId(String year , String categoryId)  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findById2(authentication.getName());
        Optional<ESGData> data = esgDataRepository
          .findByCorpIdAndYearAndCategoryId(user.getCorpId(), year, categoryId);
      return data.map(ESGData::toDTO).orElse(null);
    }

    public ESGDataDetailDTO getDetailByCorpIdAndYearAndCategoryId(String year , String categoryId)  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findById2(authentication.getName());
        UnitDTO unit = unitService.getUnitById(categoryId);

        Optional<ESGData> data = esgDataRepository
                .findByCorpIdAndYearAndCategoryId(user.getCorpId(), year, categoryId);
        return data.map(esgData -> esgData.toDetailDTO(unit)).orElse(null);
    }

    @Transactional
    public ESGDataDTO patchESGData(ESGDataFilterDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUsername();
        UserDTO user = userService.findById2(userId);

        ESGData existing = esgDataRepository
          .findByCorpIdAndYearAndCategoryId(user.getCorpId(), dto.getYear(), dto.getCategoryId())
          .orElseThrow(() -> new NotFoundException("해당 ESG 데이터가 존재하지 않습니다."));

        existing.updateValue(dto.getValue(), userId);

        return esgDataRepository.save(existing).toDTO();
    }

    @Transactional
    public ESGDataDTO createESGData2(ESGDataFilterDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUsername();
        UserDTO user = userService.findById2(userId);

        Optional<ESGData> existing = esgDataRepository
          .findByCorpIdAndYearAndCategoryId(user.getCorpId(), dto.getYear(), dto.getCategoryId());

        if (existing.isPresent()){
            throw new DuplicateException("해당 ESG 데이터가 이미 존재합니다.");
        }

        ESGData esgData = ESGData.builder()
          .categoryId(dto.getCategoryId())
          .corpId(user.getCorpId())
          .year(dto.getYear())
          .value(dto.getValue())
          .createdAt(LocalDateTime.now())
          .createdBy(userId)
          .updatedAt(LocalDateTime.now())
          .updatedBy(userId)
          .build();
        esgDataRepository.save(esgData);
        return esgData.toDTO(); // 수정: 저장된 결과 반환
    }
}
