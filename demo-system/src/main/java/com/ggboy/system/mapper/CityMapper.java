package com.ggboy.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityMapper {
    String columns = "id,parent_id,name";
    String table = "sys_city";

    @Select("select " + columns + " from " + table + " where parent_id = #{parentId,jdbcType=INTEGER}")
    List<Map<String, Object>> selectByParentId(int parentId);

    @Select("select " + columns + " from " + table + " where id = #{id,jdbcType=INTEGER}")
    Map<String, Object> selectById(int id);

    @Select("select " + columns + " from " + table)
    List<Map<String, Object>> selectAll();
}