package com.faire.ai.tinyweatherbulletin.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bulletin {

    @SerializedName(value = "dt")
    private Long  dateTimestamp;

    @SerializedName(value = "main")
    private BulletinInfo info;

}
