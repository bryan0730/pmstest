package com.forwiz.pms.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.UrlResource;

@Getter
@Setter
@AllArgsConstructor
public class MessageFileInfoResponse {

    private UrlResource resource;
    private String originExtFileName;
    private String storeFileName;
    private String uploadFileName;
}
