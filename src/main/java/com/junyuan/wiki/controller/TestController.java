package com.junyuan.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*
    :后是默认值
     */
    @Value("${test.hello:TEST}")
    private String testHello;
    /*
    Get Post Put Delete
    RequestMapping 注解支持所有http访问方式
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello World " + testHello;
    }

    @RequestMapping(value = "/hello/post", method = RequestMethod.POST)
    public String helloPost(String name){
        return "Hello World, "+name;
    }
}
