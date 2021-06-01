package com.junyuan.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.junyuan.wiki.config.WikiApplication;
import com.junyuan.wiki.domain.Doc;
import com.junyuan.wiki.domain.DocExample;
import com.junyuan.wiki.mapper.DocMapper;
import com.junyuan.wiki.req.DocQueryReq;
import com.junyuan.wiki.req.DocSaveReq;
import com.junyuan.wiki.resp.DocQueryResp;
import com.junyuan.wiki.resp.PageResp;
import com.junyuan.wiki.util.CopyUtil;
import com.junyuan.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq req){
        DocExample docExample= new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(),req.getSize());

        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo=new PageInfo<>(docList);
        LOG.info("总行数: {}",pageInfo.getTotal());
        LOG.info("总页数: {}",pageInfo.getPages());
//        List<DocResp>respList=new ArrayList<>();
//        for (Doc doc : docList) {
////            DocResp docResp = new DocResp();
////            BeanUtils.copyProperties(doc, docResp);
//            //对象复制
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            respList.add(docResp);
//        }


        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp= new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public List<DocQueryResp> all(DocQueryReq req){
        DocExample docExample= new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        List<Doc> docList = docMapper.selectByExample(docExample);


        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }

    /**
     * 保存
     */
    public void save(DocSaveReq req){
        Doc doc =CopyUtil.copy(req, Doc.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        }
        else {
            //更新
            docMapper.updateByPrimaryKey(doc);
        }

    }


    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }
}