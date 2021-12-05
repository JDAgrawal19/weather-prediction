package com.prediction.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PredictionResponse {
    private List<PredictionOfDay> list;
}
