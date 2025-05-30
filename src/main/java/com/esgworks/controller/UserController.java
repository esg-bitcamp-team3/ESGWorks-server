package com.esgworks.controller;

import com.esgworks.domain.User;
import com.esgworks.dto.UserDTO;
import com.esgworks.dto.UserSignupRequest;
import com.esgworks.dto.LoginRequest;
import com.esgworks.service.UserService;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest req) {
        boolean result = userService.signup(req);
        if (!result) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 이메일입니다.");
        }
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        User user = userService.findById(req.getId());
        if(user==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return ResponseEntity.ok("로그인 성공");
    }
    @GetMapping("/my")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        User user = userService.findById(authentication.getName());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDTO user = userService.findByName(username);
        return ResponseEntity.ok(user);
    }
}
