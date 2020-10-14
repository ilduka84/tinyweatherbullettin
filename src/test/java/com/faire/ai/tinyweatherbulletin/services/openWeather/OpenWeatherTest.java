package com.faire.ai.tinyweatherbulletin.services.openWeather;

import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletin.services.IWeather;
import com.faire.ai.tinyweatherbulletin.services.weather.OpenWeather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OpenWeatherTest {

    @Autowired
    IWeather weatherService;

    @Test(expected = WebClientResponseException.class)
    public void getWeatherBullettinsFromCityAndCountry_WebClientResponseException(){
        String city ="Fake city";
        WeatherBullettins weatherBullettins = weatherService.getWeatherBullettinsFromCityAndCountry(city,"");

    }

    @Test
    public void getWeatherBullettinsFromCityAndCountry_withResult(){
        String city ="Rome";
        WeatherBullettins weatherBullettins = weatherService.getWeatherBullettinsFromCityAndCountry(city,"");
        assertThat(weatherBullettins, instanceOf(WeatherBullettins.class));
        assertFalse(weatherBullettins.getBullettinList().isEmpty());
    }



}
