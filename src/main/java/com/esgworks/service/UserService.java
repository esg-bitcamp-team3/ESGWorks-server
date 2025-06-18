package com.esgworks.service;

import com.esgworks.domain.Corporation;
import com.esgworks.domain.User;
import com.esgworks.dto.PasswordUpdateDTO;
import com.esgworks.dto.UserDTO;
import com.esgworks.dto.UserSignupRequest;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CorporationRepository;
import com.esgworks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

//    public UserDTO toDto(User user) {
//        return UserDTO.builder()
//                .id(user.getId())        // ID
//                .password(user.getPassword())    // 카테고리 이름
//                .build();                                    // DTO 완성
//    }

    // 카테고리 정보를 업데이트하는 메서드
    public UserDTO updateUser(UserDTO dto) {
        // 현재 로그인한 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 로그인 안 된 상태면 예외 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }

        // categoryId로 기존 카테고리를 DB에서 찾아오기
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("유저 정보를 찾을 수 없습니다."));

        // 기존 카테고리 정보를 전달받은 DTO 값으로 수정
        user.setPassword(dto.getPassword());      // 이름 수정
        user.setId(dto.getId());                  // 단위 ID 수정

        // 수정된 카테고리를 DB에 저장 (실제로 update 수행)
        User updatedUser = userRepository.save(user);

        // 저장된 카테고리 엔티티를 DTO로 변환해서 리턴
        return updatedUser.toDTO();
    }


    public String updatePassword(String id, PasswordUpdateDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저 정보를 찾을 수 없습니다."));

        // 기존 비밀번호 검증
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return "기존 비밀번호가 일치하지 않습니다";
        }

        // 새 비밀번호 저장
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return "비밀번호 변경 성공";
    }

}

