package com.springcloud.webclients.api.code;

public class TransMapper {

    public static ServiceDto toServiceDto(UiRequestDto dto){

        return ServiceDto.builder()
                .format1(dto.getName())
                .format2(dto.getContent())
                .format3(dto.getLoc())
                .build();
    }
}
