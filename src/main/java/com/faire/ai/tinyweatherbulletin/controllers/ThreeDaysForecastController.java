package com.faire.ai.tinyweatherbulletin.controllers;

import com.faire.ai.tinyweatherbulletinapi.dom.ThreeDaysForecast;
import com.faire.ai.tinyweatherbulletinapi.rest.ThreeDaysForecastApi;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThreeDaysForecastController implements ThreeDaysForecastApi {

    @Override
    public ResponseEntity<ThreeDaysForecast> getThreeDaysForecast(@ApiParam(value = "country",required=true, defaultValue = "IT") @PathVariable("country") String country
            , @ApiParam(value = "city",required=true, defaultValue = "Milan") @PathVariable("city") String city
    ){
        return null;
    }

}
