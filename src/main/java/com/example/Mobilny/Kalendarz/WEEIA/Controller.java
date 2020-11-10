package com.example.Mobilny.Kalendarz.WEEIA;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/calendar")
    public void getCalendar(@RequestParam int year,@RequestParam int month ){
    }
}
