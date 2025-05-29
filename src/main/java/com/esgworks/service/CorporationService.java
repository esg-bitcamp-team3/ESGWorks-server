package com.esgworks.service;

import com.esgworks.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esgworks.domain.Corporation;

@Service
public class CorporationService {
    @Autowired
    private CorporationRepository corporationRepository;

    public Corporation getCorporationById(String corpId) {
        return corporationRepository.findById(corpId)
                .orElseThrow(()-> new RuntimeException("회사를 찾을 수 없습니다."));
    }
}
