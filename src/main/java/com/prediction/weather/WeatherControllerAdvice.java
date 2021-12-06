package com.prediction.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prediction.weather.model.ErrorResponse;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "weather-prediction-controller-advice-enabled")
public class WeatherControllerAdvice {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public ErrorResponse handleFeignException(HttpServletRequest httpServletRequest, FeignException feignException){

        HttpStatus status = HttpStatus.BAD_GATEWAY;
        try{
            final ErrorResponse cause = MAPPER.readValue(feignException.content(), ErrorResponse.class);
            final ErrorResponse response = getErrorResponse(httpServletRequest, status, feignException.getMessage());
            response.setCause(cause);
            return response;
        }catch (IOException e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Invalid");
            return errorResponse;
        }
    }

    private ErrorResponse getErrorResponse(HttpServletRequest request, HttpStatus status, String message){
        return new ErrorResponse(status.value(), message, HtmlUtils.htmlEscape(request.getRequestURL().toString()));
    }
}
