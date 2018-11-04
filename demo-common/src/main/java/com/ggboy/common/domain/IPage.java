package com.ggboy.common.domain;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ggboy.common.constant.PropertiesConstant;

public class IPage {
    private Integer currentPage;
    private Integer pageSize;
    private Boolean isCount;

    IPage(Integer currentPage) {
        if (currentPage == null) {
            this.pageSize = 0;
            this.currentPage = 0;
            this.isCount = false;
        } else {
            this.pageSize = PropertiesConstant.getDefaultSmsPageSize();
            this.currentPage = currentPage;
            this.isCount = true;
        }
    }

    IPage(Integer currentPage, Integer pageSize) {
        if (currentPage == null) {
            this.currentPage = 0;
            this.pageSize = 0;
            this.isCount = false;
        } else {
            this.currentPage = currentPage;
            this.pageSize = pageSize != null ? pageSize : PropertiesConstant.getDefaultSmsPageSize();
            this.isCount = true;
        }
    }

    public <E> Page<E> startPage() {
        return startPage(this);
    }

    public void clear() {
        clearPage();
    }

    public final static <E> Page<E> startPage(IPage iPage) {
        if (iPage == null)
            return PageHelper.startPage(0, 0, false, null, null);
        return PageHelper.startPage(iPage.currentPage, iPage.pageSize, iPage.isCount, true, null);
    }

    public final static void clearPage() {
        PageHelper.clearPage();
    }
}
