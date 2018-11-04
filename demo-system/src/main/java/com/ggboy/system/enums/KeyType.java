package com.ggboy.system.enums;

public enum KeyType {
    Member("用户", "MBR"),
    Demand("需求", "DEM")
    ;
    private String name;
    private String falg;

    KeyType(String name, String falg) {
        this.name = name;
        this.falg = falg;
    }

    public String getFalg() {
        return falg;
    }
}
