package com.insurelytics.service;

import com.insurelytics.config.JwtUtils;
import com.insurelytics.dto.AuthRequest;
import com.insurelytics.model.UserAccount;
import com.insurelytics.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handles registration and login operations.  Encodes passwords and issues JWT tokens.
 */
@Service
public class AuthService {
    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserAccountRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        UserAccount user = new UserAccount(request.getUsername(), passwordEncoder.encode(request.getPassword()), "ROLE_USER");
        userRepository.save(user);
        return jwtUtils.generateToken(user);
    }

    public String authenticate(AuthRequest request) {
        UserAccount user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtils.generateToken(user);
    }
}