package com.esgworks.service;

import com.esgworks.domain.Corporation;
import com.esgworks.domain.User;
import com.esgworks.dto.UserDTO;
import com.esgworks.dto.UserSignupRequest;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CorporationRepository;
import com.esgworks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.esgworks.domain.CorporationInfo;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CorporationRepository corporationRepository;

    public boolean signup(UserSignupRequest req) {
        if (userRepository.existsById(req.getId())) {
            return false; // 중복 이메일
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return false; // 중복 이메일
        }
        Corporation corp = corporationRepository.findById(req.getCorpId())
                .orElseThrow(()-> new RuntimeException("회사 정보가 없습니다."));


        CorporationInfo corpInfo = CorporationInfo.builder()
                .corpId(corp.getCorpId())
                .corpName(corp.getCorpName())
                .ceoName(corp.getCeoName())
                .industry(corp.getIndustry())
                .webpage(corp.getWebpage())
                .revenue(corp.getRevenue())
                .address(corp.getAddress())
                .employeeCnt(corp.getEmployeeCnt())
                .establishedDate(corp.getEstablishedDate())
                .build();

        User user = new User();
        user.setId(req.getId());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // 비밀번호 암호화!
        user.setName(req.getName());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setCorpId(req.getCorpId());
        userRepository.save(user);
        return true;
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    public UserDTO findById2(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("로그인 실패")).toDTO();
    }

}

