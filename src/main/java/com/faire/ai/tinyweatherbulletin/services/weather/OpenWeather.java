package com.faire.ai.tinyweatherbulletin.services.weather;

import com.faire.ai.tinyweatherbulletin.entities.WeatherBullettins;
import com.faire.ai.tinyweatherbulletin.services.IWeather;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Formatter;
import java.util.List;

@Service
public class OpenWeather implements IWeather {

    @Value("${weather.url}")
    String baseUrl;

    @Value("${weather.workinghours}")
    List<String> workinghours;

    @Value("${weather.path}")
    String path;

    @Value("${weather.query}")
    String query;

    @Value("${openweather.appId}")
    String appId;

    private Gson gson;

    private final WebClient client;

    public OpenWeather (){
        this.client = WebClient.create(baseUrl);
        this.gson = new Gson();
    }

    public WeatherBullettins getWeatherBullettinsFromCityAndCountry(String city, String country) throws WebClientResponseException{
        Mono<String> response = getResult(city, country, this.appId);
        WeatherBullettins weatherBullettins = this.gson.fromJson(response.block(), WeatherBullettins.class);
        return weatherBullettins;
    }

    private Mono<String> getResult(String city, String country, String apiId){
        StringBuilder newUri = new StringBuilder();
        newUri.append(baseUrl).append(path);
        Formatter formatter = new Formatter(newUri);
        formatter.format(query, city, country, apiId);

        Mono<String> mono = client.get()
                .uri(newUri.toString())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);

        return mono;
    }


}
