package com.faire.ai.tinyweatherbulletin.controllers.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherBullettinsUtilsTest {

    @Test
    void fromWeatherBullettinsTo() {

    }

    @Test
    void getNextDayAtMidnight() {
        List<String> workingHours = new ArrayList<>();
        workingHours.add("9:00");
        workingHours.add("18:00");
        List<Long> result = WeatherBullettinsUtils.getNextDayRangesInTimestamp(1,workingHours );
        assertTrue(result.size()==4);

    }

    @Test
    void getDayFrom(){

    }

    @Test
    void getNextDayRangesInTimestamp(){

    }
}