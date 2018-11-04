package com.ggboy.system.service.impl;

import com.github.pagehelper.Page;
import com.ggboy.common.domain.IPage;
import com.ggboy.system.convert.CityConvert;
import com.ggboy.system.domain.info.CityInfo;
import com.ggboy.system.domain.info.ProvinceInfo;
import com.ggboy.system.mapper.CityMapper;
import com.ggboy.system.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    @Cacheable(cacheNames = "Provinces", unless = "#result == null || #result.isEmpty()")
    public List<ProvinceInfo> getProvinces() {
        return CityConvert.convertToProvinceInfoList(cityMapper.selectByParentId(0));
    }

    @Override
    @Cacheable(cacheNames = "Cities", key = "#p0", unless = "#result == null || #result.isEmpty()")
    public List<CityInfo> getCities(int provinceId) {
        return CityConvert.convertToCityInfoList(cityMapper.selectByParentId(provinceId));
    }

    @Override
    @Cacheable(cacheNames = "City", key = "#p0", unless = "#result == null")
    public CityInfo getCityById(int id) {
        return CityConvert.convertToCityInfo(cityMapper.selectById(id));
    }

    @Override
    @Cacheable(cacheNames = "AllCities", unless = "#result == null || #result.isEmpty()")
    public List<ProvinceInfo> getCities() {
        List<CityInfo> cityList = CityConvert.convertToCityInfoList(cityMapper.selectAll());
        Map<Integer, ProvinceInfo> provinceInfoMap = new TreeMap<>();
        for (CityInfo city : cityList)
            if (city.getParentId() == 0)
                provinceInfoMap.put(city.getId(), new ProvinceInfo(city.getId(), city.getName(), true));

        for (CityInfo city : cityList)
            if (city.getParentId() != 0)
                provinceInfoMap.get(city.getParentId()).getChildren().add(city);

        return new ArrayList<>(provinceInfoMap.values());
    }

    public List<Map<String, Object>> getCitiesPageDemo(IPage iPage) {
        try {
            Page<Map<String, Object>> page = IPage.startPage(iPage);
            cityMapper.selectAll();
            return page;
        } finally {
            IPage.clearPage();
        }
    }
}
