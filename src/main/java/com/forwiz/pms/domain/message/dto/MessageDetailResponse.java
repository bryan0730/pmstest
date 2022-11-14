package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.entity.UploadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MessageDetailResponse {
    private Long receiverId;
    private Long senderId;
    private String comments;
    private Date sendDate;
    private String senderName;
    private String receiverName;
    private List<UploadFile> files;


    public MessageDetailResponse(Message message){
        this.receiverId = message.getReceiver().getId();
        this.senderId = message.getSender().getId();
        this.comments = message.getComments();
        this.sendDate = message.getSendDate();
        this.senderName = message.getSender().getUserName();
        this.receiverName = message.getReceiver().getUserName();
        this.files = message.getMessageFiles().stream()
                .map(UploadFile::new)
                .collect(Collectors.toList());
    }
}
