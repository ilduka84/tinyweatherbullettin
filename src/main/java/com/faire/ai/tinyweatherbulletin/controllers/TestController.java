package com.faire.ai.tinyweatherbulletin.controllers;

import com.faire.ai.tinyweatherbulletinapi.rest.TestApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cctest")
public class TestController implements TestApi {

    @Override
    public ResponseEntity testGet(String test) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}