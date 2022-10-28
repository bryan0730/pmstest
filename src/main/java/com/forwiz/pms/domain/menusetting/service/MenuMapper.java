package com.forwiz.pms.domain.menusetting.service;

import com.forwiz.pms.domain.menusetting.dto.MenuMapperValue;
import com.forwiz.pms.domain.menusetting.dto.MenuMapperType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuMapper {

    private final Map<String, List<MenuMapperValue>> factory = new LinkedHashMap<>();

    public void put(String key, Class<? extends MenuMapperType> e){
        factory.put(key, toMenuValues(e));
    }

    private List<MenuMapperValue> toMenuValues(Class<? extends MenuMapperType> e){
        return Arrays.stream(e.getEnumConstants())
                .map(MenuMapperValue::new)
                .collect(Collectors.toList());
    }

    public List<MenuMapperValue> get(String key){
        return factory.get(key);
    }

    public Map<String, List<MenuMapperValue>> getAll(){
        return factory;
    }
}
