package com.faire.ai.tinyweatherbulletin.services;

import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public interface IWeather {
    WeatherBullettins getWeatherBullettinsFromCityAndCountry(String city, String country) throws WebClientResponseException;
}
