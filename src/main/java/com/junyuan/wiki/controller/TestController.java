package com.junyuan.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    /*
    Get Post Put Delete
    RequestMapping 注解支持所有http访问方式
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = "/hello/post", method = RequestMethod.POST)
    public String helloPost(String name){
        return "Hello World, "+name;
    }
}
