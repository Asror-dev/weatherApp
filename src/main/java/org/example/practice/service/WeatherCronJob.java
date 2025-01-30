package org.example.practice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.practice.service.weather.WeatherDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherCronJob {
    private final WeatherDataService weatherDataService;
    @Scheduled(cron = "0 0 0 * * ?")// Har kuni 00:00 da ishga tushadi
    public void scheduleWeatherUpdate() throws JsonProcessingException {
        weatherDataService.fetchAndSaveWeatherData();
    }
}
