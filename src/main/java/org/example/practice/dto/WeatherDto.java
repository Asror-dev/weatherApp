package org.example.practice.dto;

import java.util.List;

public record WeatherDto(
        List<String> countries
) {
}
