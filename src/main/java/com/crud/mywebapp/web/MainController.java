package com.crud.mywebapp.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }
}
