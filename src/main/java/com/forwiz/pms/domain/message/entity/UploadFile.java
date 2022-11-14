package com.forwiz.pms.domain.message.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {

    private Long messageFileId;
    private String uploadFileName;
    private String storeFileName;
    private String ext;
    private Long volume;
    private String contentType;

    @Builder
    public UploadFile(String uploadFileName, String storeFileName, String ext, Long volume, String contentType){
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.ext = ext;
        this.volume = volume;
        this.contentType = contentType;
    }

    public UploadFile(MessageFile messageFile){
        this.messageFileId = messageFile.getMessageFileId();
        this.uploadFileName = messageFile.getUploadFileName();
        this.storeFileName = messageFile.getStoreFileName();
        this.ext = messageFile.getExtension();
        this.volume = messageFile.getFileVolume();
        this.contentType = messageFile.getFileContentType();
    }
}
