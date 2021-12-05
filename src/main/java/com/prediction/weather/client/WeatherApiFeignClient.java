package com.prediction.weather.client;

import com.prediction.weather.model.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.prediction.weather.client.WeatherApiFeignClient.DEFAULT_URL;

@FeignClient(name = "openWeatherApi", url = DEFAULT_URL)
public interface WeatherApiFeignClient {
    String DEFAULT_URL = "https://api.openweathermap.org";
    @GetMapping(path = "/data/2.5/forecast")
    WeatherResponse getWeatherResponse(@RequestParam("q")String city, @RequestParam("appid")String key, @RequestParam(value = "cnt", required = false, defaultValue ="")String count);
}
