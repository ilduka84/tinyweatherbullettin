package com.faire.ai.tinyweatherbulletin.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ThreeDaysForecastControllerTest {

    @InjectMocks
    ThreeDaysForecastController controller;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getThreeDaysForecast() throws Exception {
        mockMvc.perform(get("/three-days-forecast/{country}/{city}", "IT", "Rome"))
                .andExpect(status().isOk());

    }

    @Test
    void getThreeDaysForecast_404() throws Exception {
        mockMvc.perform(get("/three-days-forecast/{country}/{city}", "IT", "FAKECITY"))
                .andExpect(status().is4xxClientError());

    }
}