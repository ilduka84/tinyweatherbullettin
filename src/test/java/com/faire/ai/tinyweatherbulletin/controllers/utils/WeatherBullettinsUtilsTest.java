package com.faire.ai.tinyweatherbulletin.controllers.utils;

import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletinapi.dom.Day;
import com.faire.ai.tinyweatherbulletinapi.dom.Info;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class WeatherBullettinsUtilsTest {


    @Value("classpath:weatherResponse.json")
    Resource fileResource;

    WeatherBullettins weatherBullettins;


    @Before
    void init() throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        this.weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);


    }
//INTEGRATION
//    @Test
//    void fromWeatherBullettinsTo() throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
//        Gson gson = new Gson();
//        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
//        List<String> workingHours = new ArrayList<>();
//        workingHours.add("9:00");
//        workingHours.add("18:00");
//        WeatherBullettinsUtils.fromWeatherBullettinsTo(weatherBullettins, workingHours);
//
//    }

    @Test
    void getNextDayAtMidnight() {
        List<String> workingHours = new ArrayList<>();
        workingHours.add("9:00");
        workingHours.add("18:00");
        List<Long> result = WeatherBullettinsUtils.getNextDayRangesInTimestamp(1,workingHours );
        assertTrue(result.size()==4);

    }

    @Test
    void getDayFrom()throws IOException{
        List <Long>rangeTimestamp = new ArrayList<>();
        rangeTimestamp.add(1602504000L);
        rangeTimestamp.add(1602547200L);
        rangeTimestamp.add(1602601200L);
        rangeTimestamp.add(1602644400L);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Double averageMaxTemperatureduringWorkingHours= 287.18;
        Double averageMinTemperatureduringWorkingHours= 287.18;
        Integer averageHumidityduringWorkingHours =  63;

        Double averageMaxTemperatureoutsideWorkingHours = 289.0314285714286;
        Double averageMinTemperatureoutsideWorkingHours = 288.8957142857143;
        Integer averageHumidityoutsideWorkingHours= 59;

        Pair<Day, Integer> dayIntegerPair = WeatherBullettinsUtils.getDayFrom(this.weatherBullettins.getBullettinList(),rangeTimestamp,0);
        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMaxTemperature()==averageMaxTemperatureduringWorkingHours);
        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMinTemperature()==averageMinTemperatureduringWorkingHours);
        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageHumidity()==averageHumidityduringWorkingHours);

        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMaxTemperature()==averageMaxTemperatureoutsideWorkingHours);
        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMinTemperature()==averageMinTemperatureoutsideWorkingHours);
        assertTrue(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageHumidity()==averageHumidityoutsideWorkingHours);
    }

    @Test
    void getNextDayRangesInTimestamp(){

    }

    @Test
    void generateInfoFrom() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Double averageMaxTemperature = 289.95000000000005;
        Double averageMinTemperature = 289.7125;
        Integer  averageHumidity =  61;
        Triple<Info, Integer, Integer> infoIntegerIntegerTriple = WeatherBullettinsUtils
                .generateInfoFrom(this.weatherBullettins.getBullettinList(),1602504000L,1602547200L, 0);
        assertTrue(infoIntegerIntegerTriple.getLeft().getAverageMaxTemperature()==averageMaxTemperature);
        assertTrue(infoIntegerIntegerTriple.getLeft().getAverageMinTemperature()==averageMinTemperature);
        assertTrue(infoIntegerIntegerTriple.getLeft().getAverageHumidity()==averageHumidity);

    }

    @Test
    void generateInfoFromOutOfRange() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Integer  averageHumidity =  0;
        Triple<Info, Integer, Integer> infoIntegerIntegerTriple = WeatherBullettinsUtils
                .generateInfoFrom(this.weatherBullettins.getBullettinList(),1602601200L,1602644400L, 15);
        assertTrue(infoIntegerIntegerTriple.getLeft().getAverageHumidity()==averageHumidity);

    }
}