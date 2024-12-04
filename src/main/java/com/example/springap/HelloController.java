package com.example.springap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name) {
        return "Hello World, My Name is " + name + ". What's your name?";
    }

    @RequestMapping(value="/bye/{name}", method = RequestMethod.GET)
    public String sayBye(@PathVariable("name") String name){
        return "Bye my World " + name;
    }
