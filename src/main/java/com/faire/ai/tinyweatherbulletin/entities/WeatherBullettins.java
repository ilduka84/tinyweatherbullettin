package com.faire.ai.tinyweatherbulletin.entities;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherBullettins {

    @SerializedName(value="list")
    private List<Bulletin> bullettinList;

    @SerializedName(value="cnt")
    private int numberOfBulletin;
}
