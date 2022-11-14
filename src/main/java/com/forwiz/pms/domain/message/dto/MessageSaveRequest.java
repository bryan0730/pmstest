package com.forwiz.pms.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSaveRequest {

    private Long messageReceiver;
    private Long messageSender;
    private String comment;
    private Date sendDate;
    private List<MultipartFile> messageFiles;

}
