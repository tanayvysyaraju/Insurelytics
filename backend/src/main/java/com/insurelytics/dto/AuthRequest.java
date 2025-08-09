package com.insurelytics.dto;

import lombok.Data;

/**
 * Represents a login or registration request containing a username and password.
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}