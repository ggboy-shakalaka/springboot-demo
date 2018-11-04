package com.ggboy.system.domain.info;

import java.io.Serializable;

public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer parentId;
    private String name;

    public CityInfo(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
