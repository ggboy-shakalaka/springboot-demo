package com.ggboy.system.convert;

import com.ggboy.system.domain.info.CityInfo;
import com.ggboy.system.domain.info.ProvinceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityConvert {
    public final static CityInfo convertToCityInfo(Map<String, Object> resultMap) {
        if (resultMap == null)
            return null;
        return new CityInfo((Integer) resultMap.get("id"), (String) resultMap.get("name"), (Integer) resultMap.get("parentId"));
    }

    public final static List<CityInfo> convertToCityInfoList(List<Map<String, Object>> resultMaps) {
        if (resultMaps == null)
            return null;

        List<CityInfo> infos = new ArrayList<>(resultMaps.size());
        for (Map<String, Object> item : resultMaps)
            infos.add(convertToCityInfo(item));

        return infos;
    }

    public final static ProvinceInfo convertToProvinceInfo(Map<String, Object> resultMap) {
        if (resultMap == null)
            return null;

        ProvinceInfo info = new ProvinceInfo();
        info.setId((Integer) resultMap.get("id"));
        info.setName((String) resultMap.get("name"));

        return info;
    }

    public final static List<ProvinceInfo> convertToProvinceInfoList(List<Map<String, Object>> resultMaps) {
        if (resultMaps == null)
            return null;

        List<ProvinceInfo> infos = new ArrayList<>(resultMaps.size());
        for (Map<String, Object> item : resultMaps)
            infos.add(convertToProvinceInfo(item));

        return infos;
    }
}
