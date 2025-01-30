package org.example.practice.service;

import lombok.RequiredArgsConstructor;
import org.example.practice.dto.LoginDto;
import org.example.practice.dto.RegisterDto;
import org.example.practice.entity.User;
import org.example.practice.repository.RoleRepository;
import org.example.practice.repository.UserRepository;
import org.example.practice.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public Map<?, ?> login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.username());
        UUID id = user.getId();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );
        String jwt = jwtService.generateJwt(id.toString());
        return Map.of("access_token", jwt,  "role", user.getUserRole().getName(), "name", user.getName(),"surname",user.getSurname());
    }

    public String register(RegisterDto registerDto) {
        try {
            User user = new User();
            user.setName(registerDto.name());
            user.setSurname(registerDto.surname());
            user.setPassword(passwordEncoder.encode(registerDto.password()));
            user.setUsername(registerDto.username());
            user.setUserRole(roleRepository.findByName("ROLE_USER"));
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "User fuvaffaqqiyatli ruyhatdan o'tdi";
    }
}
