package org.example.practice.repository;

import org.example.practice.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {

    WeatherData findWeatherDataByNameEqualsIgnoreCase(String city);
}
