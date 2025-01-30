package org.example.practice.dto;

public record RegisterDto(
        String name,
        String surname,
        String username,
        String password
) {
}
