package com.esgworks.service;

import com.esgworks.domain.ESGData;
import com.esgworks.dto.ESGDataDTO;
import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.ESGDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ESGDataService {

    private final ESGDataRepository esgDataRepository;

    public Optional<ESGData> getESGDataById(String esgDataId) {
        return esgDataRepository.findById(esgDataId);
    }

    // 전체 ESG 데이터 조회
    public List<ESGDataDTO> getAllESGData() {
        return esgDataRepository.findAll().stream().map(ESGData::toDTO).toList();
    }

    // 기업 ID로 조회
    public List<ESGDataDTO> getByCorpId(String corpId) {
        return esgDataRepository.findByCorpId(corpId).stream().map(ESGData::toDTO).toList();
    }

    // 기업 ID + 연도로 단일 조회
    public List<ESGDataDTO> getByCorpIdAndYear(String corpId, String year) {
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
}
