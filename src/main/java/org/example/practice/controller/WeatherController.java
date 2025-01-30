package org.example.practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.example.practice.dto.WeatherDto;
import org.example.practice.entity.WeatherData;
import org.example.practice.service.weather.WeatherDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherDataService weatherService;

    @PostMapping("/data")
    public ResponseEntity<?> getWeatherData(@RequestBody WeatherDto weatherDto) throws JsonProcessingException {
        try {
            List<WeatherData> weatherData = weatherService.getWeatherData(weatherDto);
            return ResponseEntity.ok(weatherData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
