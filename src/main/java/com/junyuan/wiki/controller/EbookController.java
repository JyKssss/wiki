package com.junyuan.wiki.controller;

import com.junyuan.wiki.req.EbookReq;
import com.junyuan.wiki.resp.CommonResp;
import com.junyuan.wiki.resp.EbookResp;
import com.junyuan.wiki.service.EbookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {


    @Resource
    private EbookService ebookService;

    //自动映射到req中的相应属性
    //controller层不要出现domain实体
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp>> resp= new CommonResp<>();
        List<EbookResp> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}