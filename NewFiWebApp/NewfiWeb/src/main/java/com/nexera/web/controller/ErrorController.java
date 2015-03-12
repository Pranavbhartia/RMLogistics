package com.nexera.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping(value="/404.do")
    public String handle404() {
    	return "404";
    }
    
}