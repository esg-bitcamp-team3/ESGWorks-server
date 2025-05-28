package com.esgworks.service;

import com.esgworks.domain.UserDocument;
import com.esgworks.dto.UserSignupRequest;
import com.esgworks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean signup(UserSignupRequest req) {
        if (userRepository.existsById(req.getId())) {
            return false; // 중복 이메일
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return false; // 중복 이메일
        }
        UserDocument user = new UserDocument();
        user.setId(req.getId());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // 비밀번호 암호화!
        user.setName(req.getName());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setCorpId(req.getCorpId());
        userRepository.save(user);
        return true;
    }

    public UserDocument findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
}

