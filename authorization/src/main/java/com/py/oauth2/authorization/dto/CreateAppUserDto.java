package com.py.oauth2.authorization.dto;

import java.util.List;

public record CreateAppUserDto (
        String username,
        String password,
        List<String> roles){}
