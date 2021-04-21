package com.junyuan.wiki.controller;

import com.junyuan.wiki.req.EbookQueryReq;
import com.junyuan.wiki.req.EbookSaveReq;
import com.junyuan.wiki.resp.CommonResp;
import com.junyuan.wiki.resp.EbookQueryResp;
import com.junyuan.wiki.resp.PageResp;
import com.junyuan.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {


    @Resource
    private EbookService ebookService;

    //自动映射到req中的相应属性
    //controller层不要出现domain实体
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp= new CommonResp<>();
        PageResp<EbookQueryResp> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResp save(@Valid @RequestBody EbookSaveReq req){
        CommonResp resp= new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp= new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}