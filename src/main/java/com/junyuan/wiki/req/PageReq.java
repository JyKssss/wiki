package com.junyuan.wiki.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 同名参数自动映射
 */
public class PageReq {
    @NotNull(message = "[页码] 不能为空")
    private int page;

    @NotNull(message = "[每页条数] 不能为空")
    @Max(value = 1000,message = "每页查询条数不能超过1000条")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}