package com.faire.ai.tinyweatherbulletin.controllers;

import com.faire.ai.tinyweatherbulletin.services.IWeather;
import com.faire.ai.tinyweatherbulletin.services.weather.OpenWeather;
import com.faire.ai.tinyweatherbulletinapi.rest.TestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cctest")
public class TestController implements TestApi {

    @Autowired
    IWeather openWeather;

    @Override
    public ResponseEntity testGet(String test) {
        openWeather.getWeatherBullettinsFromCityAndCountry("Rome", "IT");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}