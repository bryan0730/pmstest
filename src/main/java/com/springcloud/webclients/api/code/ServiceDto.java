package com.springcloud.webclients.api.code;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder

public class ServiceDto extends UiRequestDto{

    private String format1;
    private String format2;
    private String format3;

}
