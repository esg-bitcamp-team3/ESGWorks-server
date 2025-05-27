package com.esgworks.controller;

import com.esgworks.dto.UserSignupRequest;
import com.esgworks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest req) {
        boolean result = userService.signup(req);
        if (!result) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 이메일입니다.");
        }
        return ResponseEntity.ok("회원가입 성공");
    }
}
