package org.example.practice.response;

import java.time.LocalDateTime;

public record WeatherResDto(
         String name, // Shahar nomi (masalan, Tashkent)
         String country, // Davlat nomi (masalan, Uzbekistan)

         double lat, // Kenglik (latitude)
         double lon, // Uzunlik (longitude)

         double tempC, // Harorat (Selsiy bo‘yicha)
         String tempColor, // Haroratga mos rang kodi

         double windKph, // Shamol tezligi (km/soat)
         String windColor, // Shamol tezligiga mos rang kodi

         int cloud, // Bulutlilik foizi
         String cloudColor, // Bulutlilik foiziga mos rang kodi

         String date //
) {
}
