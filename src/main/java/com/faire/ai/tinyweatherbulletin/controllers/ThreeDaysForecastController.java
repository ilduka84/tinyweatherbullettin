package com.faire.ai.tinyweatherbulletin.controllers;

import com.faire.ai.tinyweatherbulletin.controllers.utils.WeatherBullettinsUtils;
import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletin.services.IWeather;
import com.faire.ai.tinyweatherbulletinapi.dom.GenericError;
import com.faire.ai.tinyweatherbulletinapi.dom.ThreeDaysForecast;
import com.faire.ai.tinyweatherbulletinapi.rest.ThreeDaysForecastApi;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Controller
@RequestMapping("/")
public class ThreeDaysForecastController implements ThreeDaysForecastApi {

    @Autowired
    IWeather weatherService;

    @Value("${weather.workinghours}")
    List<String> workingHours;

    @Override
    public ResponseEntity getThreeDaysForecast(@ApiParam(value = "country",required=true, defaultValue = "IT") @PathVariable("country") String country
            , @ApiParam(value = "city",required=true, defaultValue = "Milan") @PathVariable("city") String city
    ){

        try {
            WeatherBullettins weatherBullettins = weatherService.getWeatherBullettinsFromCityAndCountry(city, country);
            ThreeDaysForecast threeDaysForecast = WeatherBullettinsUtils.fromWeatherBullettinsTo(weatherBullettins,workingHours);
            return new ResponseEntity<>(threeDaysForecast,HttpStatus.OK);
        }catch(WebClientResponseException e){
            GenericError genericError = new GenericError();
            genericError.setCod(e.getRawStatusCode());
            genericError.setMessage(e.getMessage());
            return new ResponseEntity(genericError, e.getStatusCode());

        }
    }

}
