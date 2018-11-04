package com.ggboy.system.domain.info;

import com.ggboy.common.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProvinceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private List<CityInfo> children;

    public ProvinceInfo() {
    }

    public ProvinceInfo(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public ProvinceInfo(Integer id, String name, boolean flag) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
        children.add(new CityInfo(this.id, StringUtil.toString("全", this.name), 0));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityInfo> getChildren() {
        return children;
    }

    public void setChildren(List<CityInfo> children) {
        this.children = children;
    }
}
