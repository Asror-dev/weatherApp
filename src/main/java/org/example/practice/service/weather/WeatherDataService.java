package org.example.practice.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.practice.dto.WeatherDto;
import org.example.practice.entity.WeatherData;

import java.util.List;

public interface WeatherDataService {
    List<WeatherData> getWeatherData(WeatherDto weatherDto) throws JsonProcessingException;

    void fetchAndSaveWeatherData() throws JsonProcessingException;

}
