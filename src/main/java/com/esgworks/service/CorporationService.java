package com.esgworks.service;

import com.esgworks.domain.Corporation;
import com.esgworks.dto.CorporationDTO;
import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporationService {

    private final CorporationRepository corporationRepository;

    // 전체 기업 목록 조회
    public List<CorporationDTO> getAllCorporations() {
        return corporationRepository.findAll().stream().map(Corporation::toDTO).toList();
    }

    // 기업 ID로 단일 조회
    public CorporationDTO getCorporationById(String corpId) {
        Corporation corporation = corporationRepository.findByCorpId(corpId).orElseThrow(() -> new NotFoundException("해당 기업이 존재하지 않습니다."));
        return corporation.toDTO();
    }

    // 새 기업 저장
    public CorporationDTO createCorporation(CorporationDTO corporationDTO) {
        Optional<Corporation> optionalCorporation = corporationRepository.findByCorpId(corporationDTO.getCorpId());
        log.info("optionalCorporation: {}", optionalCorporation);
        if(optionalCorporation.isPresent()){
            throw new DuplicateException("이미 있는 기업");
        }
        Corporation corporation = Corporation.builder()
                .corpId(corporationDTO.getCorpId())
                .corpName(corporationDTO.getCorpName())
                .ceoName(corporationDTO.getCeoName())
                .industry(corporationDTO.getIndustry())
                .webpage(corporationDTO.getWebpage())
                .revenue(corporationDTO.getRevenue())
                .employeeCnt(corporationDTO.getEmployeeCnt())
                .establishedDate(corporationDTO.getEstablishedDate())
                .address(corporationDTO.getAddress())
                .build();
        corporationRepository.save(corporation);
        return corporationDTO;
    }

    // 기업 정보 수정
    public CorporationDTO updateCorporation(String corpId, CorporationDTO updatedCorporation) {


        corporationRepository.findByCorpId(corpId)
                .map(existing -> {
                    Corporation updated = Corporation.builder()
                            .corpId(existing.getCorpId())
                            .corpName(updatedCorporation.getCorpName())
                            .ceoName(updatedCorporation.getCeoName())
                            .industry(updatedCorporation.getIndustry())
                            .webpage(updatedCorporation.getWebpage())
                            .revenue(updatedCorporation.getRevenue())
                            .address(updatedCorporation.getAddress())
                            .employeeCnt(updatedCorporation.getEmployeeCnt())
                            .establishedDate(updatedCorporation.getEstablishedDate())
                            .build();
                    return corporationRepository.save(updated);
                })
                .orElseThrow(() -> new NotFoundException("해당 기업이 존재하지 않습니다."));

        return updatedCorporation;
    }

    // 기업 삭제
    public void deleteCorporation(String corpId) {
        Corporation corporation = corporationRepository.findByCorpId(corpId)
                .orElseThrow(() -> new NotFoundException("해당 기업이 존재하지 않습니다."));

        corporationRepository.delete(corporation);
    }
}
