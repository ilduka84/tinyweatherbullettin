package com.faire.ai.tinyweatherbulletin.controllers.utils;

import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletinapi.dom.Day;
import com.faire.ai.tinyweatherbulletinapi.dom.Info;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
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
public class WeatherBullettinsUtilsTest {


    @Value("classpath:weatherResponse.json")
    Resource fileResource;

    WeatherBullettins weatherBullettins;


    @Before
    public void init() throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        this.weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);


    }

    @Test
    public void getNextDayAtMidnight() {
        List<String> workingHours = new ArrayList<>();
        workingHours.add("9:00");
        workingHours.add("18:00");
        List<Long> result = WeatherBullettinsUtils.getNextDayRangesInTimestamp(1,workingHours );
        assertTrue(result.size()==4);

    }

    @Test
    public void getDayFrom()throws IOException{
        List <Long>rangeTimestamp = new ArrayList<>();
        rangeTimestamp.add(1602504000L);
        rangeTimestamp.add(1602547200L);
        rangeTimestamp.add(1602601200L);
        rangeTimestamp.add(1602644400L);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Double averageMaxTemperatureduringWorkingHours= 287.36333333333334;
        Double averageMinTemperatureduringWorkingHours= 287.36333333333334;
        Integer averageHumidityduringWorkingHours =  64;

        Double averageMaxTemperatureoutsideWorkingHours = 288.93125000000003;
        Double averageMinTemperatureoutsideWorkingHours = 288.8125;
        Integer averageHumidityoutsideWorkingHours= 60;

        Pair<Day, Integer> dayIntegerPair = WeatherBullettinsUtils.getDayFrom(this.weatherBullettins.getBullettinList(),rangeTimestamp,0);
        assertEquals(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMaxTemperature(),averageMaxTemperatureduringWorkingHours);
        assertEquals(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageMinTemperature(),averageMinTemperatureduringWorkingHours);
        assertEquals(dayIntegerPair.getLeft().getDuringWorkingHours().getAverageHumidity(),averageHumidityduringWorkingHours);

        assertEquals(dayIntegerPair.getLeft().getOutsideWorkingHours().getAverageMaxTemperature(),averageMaxTemperatureoutsideWorkingHours);
        assertEquals(dayIntegerPair.getLeft().getOutsideWorkingHours().getAverageMinTemperature(),averageMinTemperatureoutsideWorkingHours);
        assertEquals(dayIntegerPair.getLeft().getOutsideWorkingHours().getAverageHumidity(),averageHumidityoutsideWorkingHours);
    }


    @Test
    public void generateInfoFrom() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Double averageMaxTemperature = 288.9920000000001;
        Double averageMinTemperature = 288.802;
        Integer  averageHumidity =  64;
        Triple<Info, Integer, Integer> infoIntegerIntegerTriple = WeatherBullettinsUtils
                .generateInfoFrom(this.weatherBullettins.getBullettinList(),1602504000L,1602547200L, 0);
        assertEquals(infoIntegerIntegerTriple.getLeft().getAverageMaxTemperature(),averageMaxTemperature);
        assertEquals(infoIntegerIntegerTriple.getLeft().getAverageMinTemperature(),averageMinTemperature);
        assertEquals(infoIntegerIntegerTriple.getLeft().getAverageHumidity(),averageHumidity);

    }

    @Test
    public void generateInfoFromOutOfRange() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileResource.getInputStream()));
        Gson gson = new Gson();
        weatherBullettins = gson.fromJson(bufferedReader, WeatherBullettins.class);
        Integer  averageHumidity =  0;
        Triple<Info, Integer, Integer> infoIntegerIntegerTriple = WeatherBullettinsUtils
                .generateInfoFrom(this.weatherBullettins.getBullettinList(),1602601200L,1602644400L, 15);
        assertTrue(infoIntegerIntegerTriple.getLeft().getAverageHumidity()==averageHumidity);

    }
}