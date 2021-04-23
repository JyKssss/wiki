package com.junyuan.wiki.controller;

import com.junyuan.wiki.req.CategoryQueryReq;
import com.junyuan.wiki.req.CategorySaveReq;
import com.junyuan.wiki.resp.CommonResp;
import com.junyuan.wiki.resp.CategoryQueryResp;
import com.junyuan.wiki.resp.PageResp;
import com.junyuan.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Resource
    private CategoryService categoryService;

    //自动映射到req中的相应属性
    //controller层不要出现domain实体
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp= new CommonResp<>();
        PageResp<CategoryQueryResp> list=categoryService.list(req);
        resp.setContent(list);
        return resp;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp= new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp= new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}