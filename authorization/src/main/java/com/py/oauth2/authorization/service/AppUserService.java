package com.py.oauth2.authorization.service;

import com.py.oauth2.authorization.domain.AppUser;
import com.py.oauth2.authorization.domain.Role;
import com.py.oauth2.authorization.dto.CreateAppUserDto;
import com.py.oauth2.authorization.dto.MessageDto;
import com.py.oauth2.authorization.repository.AppUserRepository;
import com.py.oauth2.authorization.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto createUser(CreateAppUserDto dto){
        AppUser appUser = AppUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        Set<Role> roles = new HashSet<>();
        dto.roles().forEach(r -> {
            Role role = repository.findByName(r)
                    .orElseThrow(()-> new RuntimeException("Role not found"));
            roles.add(role);
        });
        appUser.setRoles(roles);
        appUserRepository.save(appUser);

        return new MessageDto("User " + appUser.getUsername() + " saved");
    }
}
