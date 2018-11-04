package com.ggboy.system.service;

import com.ggboy.system.domain.info.CityInfo;
import com.ggboy.system.domain.info.ProvinceInfo;

import java.util.List;

public interface CityService {
    List<ProvinceInfo> getProvinces();

    List<CityInfo> getCities(int provinceId);

    CityInfo getCityById(int id);

    List<ProvinceInfo> getCities();
}
