package com.prediction.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionOfDay{
    private String date;
    private Integer minTemp;
    private Integer maxTemp;
    private String prediction;
}
