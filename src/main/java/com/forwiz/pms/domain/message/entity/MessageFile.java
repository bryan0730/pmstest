package com.forwiz.pms.domain.message.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name="MESSAGE_FILE_SEQ_GENERATOR", sequenceName = "MESSAGE_FILE_SEQ", allocationSize = 1)
public class MessageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_FILE_SEQ_GENERATOR")
    private Long messageFileId;

    @Column
    private String uploadFileName;

    @Column
    private String storeFileName;

    @Column
    private String extension;

    @Column
    private Long fileVolume;

    @Column
    private String fileContentType;

    @CreatedDate
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="message_id", nullable = false)
    private Message message;

    @Builder
    public MessageFile(Long messageFileId, String uploadFileName, String storeFileName, String extension, Long fileVolume, String fileContentType, Message message){
        this.messageFileId = messageFileId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
        this.fileVolume = fileVolume;
        this.fileContentType = fileContentType;
        this.message = message;
    }
}
