package com.esgworks.controller;

import com.esgworks.domain.User;
import com.esgworks.dto.JwtRes;
import com.esgworks.dto.LoginRequest;
import com.esgworks.security.JwtTokenProvider;
import com.esgworks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtRes> login(@RequestBody LoginRequest req) throws Exception {
        User user = userService.findById(req.getId());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new Exception("아이디 또는 비밀번호가 올바르지 않습니다.") ;
//              ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getId());

//        return ResponseEntity.ok().body(Map.of("token", token, "message", "로그인 성공"));
        return ResponseEntity.ok(JwtRes.builder().token(token).build());
    }
}
