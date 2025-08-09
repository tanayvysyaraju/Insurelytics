package com.insurelytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response returned after successful authentication or registration, containing the JWT token.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}