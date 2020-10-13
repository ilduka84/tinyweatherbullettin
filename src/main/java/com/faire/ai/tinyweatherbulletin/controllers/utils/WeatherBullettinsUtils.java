package com.faire.ai.tinyweatherbulletin.controllers.utils;


import com.faire.ai.tinyweatherbulletin.entities.Bulletin;
import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletinapi.dom.Day;
import com.faire.ai.tinyweatherbulletinapi.dom.Info;
import com.faire.ai.tinyweatherbulletinapi.dom.ThreeDaysForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;

//WORKING HOURS IN UTC
public class WeatherBullettinsUtils {

    public static ThreeDaysForecast fromWeatherBullettinsTo(WeatherBullettins weatherBullettins, List<String> workingHours){
        List<Bulletin> bulletins = weatherBullettins.getBullettinList();
        List<Long> rangeTimestamp;
        ThreeDaysForecast threeDaysForecast = new ThreeDaysForecast();
        int index = 0;
        rangeTimestamp = getNextDayRangesInTimestamp(1, workingHours);
        Pair<Day, Integer> dayIndex1 = getDayFrom(bulletins, rangeTimestamp, index);
        rangeTimestamp = getNextDayRangesInTimestamp(2, workingHours);
        index += dayIndex1.getRight();
        Pair<Day, Integer> dayIndex2 = getDayFrom(bulletins, rangeTimestamp, index);
        rangeTimestamp = getNextDayRangesInTimestamp(3, workingHours);
        index += dayIndex1.getRight();
        Pair<Day, Integer> dayIndex3 = getDayFrom(bulletins, rangeTimestamp, index);
        threeDaysForecast.setDay1(dayIndex1.getLeft());
        threeDaysForecast.setDay2(dayIndex2.getLeft());
        threeDaysForecast.setDay1(dayIndex3.getLeft());
        return threeDaysForecast;
    }

    public static Pair<Day, Integer> getDayFrom(List<Bulletin> bulletins,List<Long>rangeTimestamp, int index){
        Triple<Info, Integer, Integer> infoNumberIndexBeforeWorkDay = generateInfoFrom(bulletins, rangeTimestamp.get(0), rangeTimestamp.get(1), index);
        index += infoNumberIndexBeforeWorkDay.getRight();
        Triple<Info, Integer,Integer> infoNumberIndexInsideWorkDay = generateInfoFrom(bulletins, rangeTimestamp.get(1), rangeTimestamp.get(2), index);
        index += infoNumberIndexInsideWorkDay.getRight();
        Triple<Info, Integer, Integer> infoNumberIndexAfterWorkDay = generateInfoFrom(bulletins, rangeTimestamp.get(2), rangeTimestamp.get(3), index);
        index += infoNumberIndexInsideWorkDay.getRight();
        Info infoOutsideWork = merge(infoNumberIndexBeforeWorkDay, infoNumberIndexAfterWorkDay);
        Day day = new Day();
        day.setDuringWorkingHours(infoNumberIndexInsideWorkDay.getLeft());
        day.setOutsideWorkingHours(infoOutsideWork);
        return Pair.of(day,new Integer(index));
    }

    private static Triple<Info,Integer, Integer> generateInfoFrom(List<Bulletin> bulletins, long startTimestamp, long endTimestamp, int startIndex){
        double maxTemperature;
        double minTemperature;
        int humidity;
        Info infoDay = new Info();
        Integer numberOfElements=0;
        for(; startIndex<bulletins.size();startIndex++){
            if(bulletins.get(startIndex).getDateTimestamp() >= startTimestamp && bulletins.get(startIndex).getDateTimestamp() < endTimestamp){
                maxTemperature = bulletins.get(startIndex).getInfo().getTempMax().doubleValue();
                minTemperature = bulletins.get(startIndex).getInfo().getTempMax().doubleValue();
                humidity = bulletins.get(startIndex).getInfo().getTempMax().intValue();
                infoDay.setAverageMaxTemperature(infoDay.getAverageMaxTemperature()+maxTemperature);
                infoDay.setAverageMinTemperature(infoDay.getAverageMinTemperature()+minTemperature);
                infoDay.setAverageHumidity(infoDay.getAverageHumidity()+humidity);
                numberOfElements++;
            }
        }
        infoDay.setAverageMaxTemperature(infoDay.getAverageMaxTemperature()/numberOfElements);
        infoDay.setAverageMinTemperature(infoDay.getAverageMinTemperature()/numberOfElements);
        infoDay.setAverageHumidity(infoDay.getAverageHumidity()/numberOfElements);
        return Triple.of(infoDay,numberOfElements , new Integer(startIndex));
    }

    private static Info merge(Triple<Info, Integer, Integer> info1, Triple<Info, Integer, Integer> info2){
        Info info = new Info();
        double maxTemperature
                = (info1.getLeft().getAverageMaxTemperature() * info1.getMiddle()
                + info2.getLeft().getAverageMaxTemperature() * info2.getMiddle())
                / (info1.getMiddle() + info2.getMiddle());
        double minTemperature
                = (info1.getLeft().getAverageMinTemperature() * info1.getMiddle()
                + info2.getLeft().getAverageMinTemperature() * info2.getMiddle())
                / (info1.getMiddle() + info2.getMiddle());
        int humidity
                = (info1.getLeft().getAverageHumidity() * info1.getMiddle()
                + info2.getLeft().getAverageHumidity() * info2.getMiddle())
                / (info1.getMiddle() + info2.getMiddle());
        info.setAverageHumidity(humidity);
        info.setAverageMaxTemperature(maxTemperature);
        info.setAverageMinTemperature(minTemperature);
        return info;
    }

//    [outsideWorkingStart, insideWorkingStart,insideWorkingEnd,outsideWorkingEnd]
    public static List<Long> getNextDayRangesInTimestamp(int delta, List<String> workingHours ){
        List<Long> rangeTimestamp= new ArrayList<>(4);
        addRangeToListFromString(rangeTimestamp, delta, "00:00");
        addRangeToListFromString(rangeTimestamp, delta, workingHours.get(0));
        addRangeToListFromString(rangeTimestamp, delta, "23:59");
        addRangeToListFromString(rangeTimestamp, delta, workingHours.get(1));
        return rangeTimestamp;
    }

    private static void addRangeToListFromString(List<Long> rangeTimestamp, int delta, String time){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH, delta);
        rangeTimestamp.add(calendar.getTimeInMillis()/1000);
    }


}
