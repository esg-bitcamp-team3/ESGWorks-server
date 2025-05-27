package com.esgworks.service;

import com.esgworks.domain.UserDocument;
import com.esgworks.dto.UserSignupRequest;
import com.esgworks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signup(UserSignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return false; // 중복 이메일
        }
        UserDocument user = new UserDocument();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // 비밀번호 암호화!
        user.setName(req.getName());
        user.setPhoneNumber(req.getPhoneNumber());
        userRepository.save(user);
        return true;
    }
}
