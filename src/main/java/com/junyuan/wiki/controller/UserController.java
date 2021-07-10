package com.junyuan.wiki.controller;

import com.junyuan.wiki.req.UserQueryReq;
import com.junyuan.wiki.req.UserSaveReq;
import com.junyuan.wiki.resp.CommonResp;
import com.junyuan.wiki.resp.UserQueryResp;
import com.junyuan.wiki.resp.PageResp;
import com.junyuan.wiki.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;

    //自动映射到req中的相应属性
    //controller层不要出现domain实体
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp list(@Valid UserQueryReq req){
        CommonResp<PageResp<UserQueryResp>> resp= new CommonResp<>();
        PageResp<UserQueryResp> list=userService.list(req);
        resp.setContent(list);
        return resp;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        CommonResp resp= new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp= new CommonResp<>();
        userService.delete(id);
        return resp;
    }
}