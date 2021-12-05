package com.prediction.weather.service;

import com.prediction.weather.client.WeatherApiFeignClient;
import com.prediction.weather.model.PredictionOfDay;
import com.prediction.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherPredictionService {

    @Value("${openweather-api-key}")
    private String openWeatherApiKey;

    private final WeatherApiFeignClient weatherApiFeignClient;


    @Autowired
    public WeatherPredictionService(WeatherApiFeignClient weatherApiFeignClient) {
        this.weatherApiFeignClient = weatherApiFeignClient;
    }

    public Map<String, PredictionOfDay> getWeatherAPIResponse(){
        WeatherResponse weatherResponse = weatherApiFeignClient.getWeatherResponse("london", openWeatherApiKey,"");
        Map<String, List<WeatherResponse.list>> weatherResponsebyDate = weatherResponse.getList().stream().collect(Collectors.groupingBy(list -> list.getDatetime().substring(0,10)));
        Map<String, PredictionOfDay> predictionOfDayMap= new HashMap<>();
        weatherResponsebyDate.forEach((key, value) -> {
            PredictionOfDay predictionOfDay  = new PredictionOfDay();
            predictionOfDay.setDate(key);
            Integer minTemp = value.stream().min(Comparator.comparing(list -> list.getMain().getTempMin())).get().getMain().getTempMin();
            Integer maxTemp = value.stream().max(Comparator.comparing(list -> list.getMain().getTempMax())).get().getMain().getTempMax();
            predictionOfDay.setMinTemp(minTemp);
            predictionOfDay.setMaxTemp(maxTemp);
            predictionOfDay.setPrediction(getPredictionforDay(value,maxTemp));
            predictionOfDayMap.put(key, predictionOfDay);
        });
        return predictionOfDayMap;
    }

    private String getPredictionforDay(List<WeatherResponse.list> weatherList, Integer maxTemp){
        String sb = "";
        Integer windSpeed = weatherList.stream().max(Comparator.comparing(list -> list.getWind().getSpeed())).get().getWind().getSpeed();
        if(maxTemp >= 273.15 + 40)
            sb+= "It will be hot outside, Wear Sunscreen";
        else if(windSpeed > 40*.447)
            sb+= "It’s too windy, watch out!";

        return sb;
    }
}
