package com.junyuan.wiki.controller;

import com.junyuan.wiki.req.DocQueryReq;
import com.junyuan.wiki.req.DocSaveReq;
import com.junyuan.wiki.resp.DocQueryResp;
import com.junyuan.wiki.resp.CommonResp;
import com.junyuan.wiki.resp.PageResp;
import com.junyuan.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {


    @Resource
    private DocService docService;

    //自动映射到req中的相应属性
    //controller层不要出现domain实体
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp list(@Valid DocQueryReq req){
        CommonResp<PageResp<DocQueryResp>> resp= new CommonResp<>();
        PageResp<DocQueryResp> list=docService.list(req);
        resp.setContent(list);
        return resp;
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResp all(@Valid DocQueryReq req){
        CommonResp<List<DocQueryResp>> resp= new CommonResp<>();
        List<DocQueryResp> list=docService.all(req);
        resp.setContent(list);
        return resp;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResp save(@Valid @RequestBody DocSaveReq req){
        CommonResp resp= new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp= new CommonResp<>();
        docService.delete(id);
        return resp;
    }
}