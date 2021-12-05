package com.prediction.weather.controller;

import com.prediction.weather.model.PredictionOfDay;
import com.prediction.weather.model.WeatherResponse;
import com.prediction.weather.service.WeatherPredictionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WeatherPredictionController {

    private final WeatherPredictionService weatherPredictionService;

    public WeatherPredictionController(WeatherPredictionService weatherPredictionService) {
        this.weatherPredictionService = weatherPredictionService;
    }

    @GetMapping
    public Map<String, PredictionOfDay> getWeatherforCity(){
        return weatherPredictionService.getWeatherAPIResponse();
    }
}
