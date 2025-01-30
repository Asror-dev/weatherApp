package org.example.practice.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.practice.dto.WeatherDto;
import org.example.practice.entity.WeatherData;
import org.example.practice.repository.WeatherDataRepository;
import org.example.practice.response.CountryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherDataServiceImpl implements WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    @Value("${weather.web.api.key}")
    private String apiKey;

    private String getJsonResponse(String city) {
        String url = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city + "&q=no";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    public List<WeatherData> getWeatherData(WeatherDto weatherDto) throws JsonProcessingException {
        List<WeatherData> weatherDataList = new ArrayList<>();
        for (String city : weatherDto.countries()) {
            String jsonResponse = getJsonResponse(city);


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode jsonNode = root.path("location");
            WeatherData weatherData = weatherDataRepository.findWeatherDataByNameEqualsIgnoreCase(city);
            JsonNode currentNode = root.path("current");


            String name = jsonNode.path("name").asText();
            String country = jsonNode.path("country").asText();
            double lat = jsonNode.path("lat").asDouble();
            double lon = jsonNode.path("lon").asDouble();
            double tempC = currentNode.path("temp_c").asDouble();
            String tempColor = getTempColor(tempC);
            double windKph = currentNode.path("wind_kph").asDouble();
            String windColor = getWindColor(windKph);
            double cloud = currentNode.path("cloud").asDouble();
            String cloudColor = getCloudColor(cloud);
            String date = jsonNode.path("localtime").asText();

            WeatherData weatherData1 = updateWeatherData(weatherData, name, country, lat, lon, tempC, tempColor, windKph, windColor, cloud, cloudColor, date);

            weatherDataList.add(weatherData1);
        }
        return weatherDataList;
    }

    private WeatherData updateWeatherData(WeatherData weatherData, String name, String country, double lat, double lon, double tempC, String tempColor, double windKph, String windColor, double cloud, String cloudColor, String date) {
        if (weatherData == null) {
            weatherData = new WeatherData();
            weatherData.setName(name);
            weatherData.setCountry(country);
            weatherData.setLat(lat);
            weatherData.setLon(lon);
            weatherData.setTempC(tempC);
            weatherData.setTempColor(tempColor);
            weatherData.setWindKph(windKph);
            weatherData.setWindColor(windColor);
            weatherData.setCloud(cloud);
            weatherData.setCloudColor(cloudColor);
            weatherData.setDate(date);
            return weatherDataRepository.save(weatherData);
        } else {

            weatherData.setName(name);
            weatherData.setCountry(country);
            weatherData.setLat(lat);
            weatherData.setLon(lon);
            weatherData.setTempC(tempC);
            weatherData.setTempColor(tempColor);
            weatherData.setWindKph(windKph);
            weatherData.setWindColor(windColor);
            weatherData.setCloud(cloud);
            weatherData.setCloudColor(cloudColor);
            weatherData.setDate(date);
            return weatherDataRepository.save(weatherData);
        }
    }


    public void fetchAndSaveWeatherData() throws JsonProcessingException {

        List<String> cities = getAllCapitals();
        System.out.println(cities);
        for (String city : cities) {
            String jsonResponse = getJsonResponse(city);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode jsonNode = root.path("location");
            JsonNode currentNode = root.path("current");
            WeatherData weatherData = weatherDataRepository.findWeatherDataByNameEqualsIgnoreCase(city);

            String name = jsonNode.path("name").asText();
            String country = jsonNode.path("country").asText();
            double lat = jsonNode.path("lat").asDouble();
            double lon = jsonNode.path("lon").asDouble();
            double tempC = currentNode.path("temp_c").asDouble();
            String tempColor = getTempColor(tempC);
            double windKph = currentNode.path("wind_kph").asDouble();
            String windColor = getWindColor(windKph);
            double cloud = currentNode.path("cloud").asDouble();
            String cloudColor = getCloudColor(cloud);
            String date = jsonNode.path("localtime").asText();

            updateWeatherData(weatherData, name, country, lat, lon, tempC, tempColor, windKph, windColor, cloud, cloudColor, date);



        }


    }

    private List<String> getAllCapitals() {
        String url = "https://restcountries.com/v3.1/all";
        RestTemplate restTemplate = new RestTemplate();
        CountryResponse[] countries = restTemplate.getForObject(url, CountryResponse[].class);

        if (countries != null) {
            return List.of(countries).stream()
                    .filter(country -> country.getCapital() != null && !country.getCapital().isEmpty())
                    .map(country -> country.getCapital().get(0))
                    .collect(Collectors.toList());
        }
        return List.of();

    }


    private String getTempColor(double tempC) {
        if (tempC <= -30) return "#003366";
        if (tempC <= -20) return "#4A90E2";
        if (tempC <= -10) return "#B3DFFD";
        if (tempC <= 0) return "#E6F7FF";
        if (tempC <= 10) return "#D1F2D3";
        if (tempC <= 20) return "#FFFACD";
        if (tempC <= 30) return "#FFCC80";
        if (tempC <= 40) return "#FF7043";
        return "#D32F2F";
    }

    private String getWindColor(double windKph) {
        if (windKph <= 10) return "#E0F7FA";
        if (windKph <= 20) return "#B2EBF2";
        if (windKph <= 40) return "#4DD0E1";
        if (windKph <= 60) return "#0288D1";
        return "#01579B";
    }

    private String getCloudColor(double cloud) {
        if (cloud <= 10) return "#FFF9C4";
        if (cloud <= 30) return "#FFF176";
        if (cloud <= 60) return "#E0E0E0";
        if (cloud <= 90) return "#9E9E9E";
        return "#616161";
    }
}
