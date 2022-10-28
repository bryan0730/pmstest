package com.forwiz.pms.domain.code;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UiRequestDto {

    private String name;
    private String loc;
    private String content;

    public ServiceDto toServiceDto() {

        return ServiceDto.builder()
                .format2(name)
                .format2(loc)
                .format3(content)
                .build();
    }
}
