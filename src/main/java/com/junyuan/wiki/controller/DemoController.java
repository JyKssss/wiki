package com.junyuan.wiki.controller;

import com.junyuan.wiki.domain.Demo;
import com.junyuan.wiki.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @Resource
    private DemoService demoService;



    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Demo> list(){
        return demoService.list();
    }
}
