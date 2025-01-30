package org.example.practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name; // Shahar nomi (masalan, Tashkent)
    private String country; // Davlat nomi (masalan, Uzbekistan)

    private double lat; // Kenglik (latitude)
    private double lon; // Uzunlik (longitude)

    private double tempC; // Harorat (Selsiy bo‘yicha)
    private String tempColor; // Haroratga mos rang kodi

    private double windKph; // Shamol tezligi (km/soat)
    private String windColor; // Shamol tezligiga mos rang kodi

    private double cloud; // Bulutlilik foizi
    private String cloudColor; // Bulutlilik foiziga mos rang kodi

    private String date; //

}
