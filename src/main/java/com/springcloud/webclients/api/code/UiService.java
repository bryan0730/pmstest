package com.springcloud.webclients.api.code;

import org.springframework.stereotype.Service;

@Service
public class UiService {

    public UiResponseDto create(UiRequestDto serviceDto){

        /*
        ...
        business logic
        ...

        UiEntity entity = uiRepository.create(serviceDto);

        UiResponseDto dto = new UiResponseDto(entity);
        or
        UiResponseDto dto = new UiResponseDto(uiRepository.create(serviceDto));
        or
        return new UiResponseDto(entity);
         */

        return new UiResponseDto();
    }
}
