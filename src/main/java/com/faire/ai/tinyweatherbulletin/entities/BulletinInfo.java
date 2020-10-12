package com.faire.ai.tinyweatherbulletin.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BulletinInfo {

    @SerializedName(value = "temp_min")
    private Double tempMin;

    @SerializedName(value = "temp_max")
    private Double tempMax;

    @SerializedName(value = "humidity")
    private Integer humidity;

}
