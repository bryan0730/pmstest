package com.forwiz.pms.domain.message.entity;

import com.forwiz.pms.domain.message.dto.MessageState;
import com.forwiz.pms.domain.message.exception.MessageException;
import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name="MESSAGE_SEQ_GENERATOR", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GENERATOR")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PmsUser sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PmsUser receiver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @Column
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column
    private MessageState messageState;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private List<MessageFile> messageFiles = new ArrayList<>();

    @Builder
    public Message(Long messageId, PmsUser sender, PmsUser receiver, Date sendDate, String comments, MessageState messageState, List<MessageFile> messageFiles){
        validatedAuthorityOfMessageCreation(sender, receiver);
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = sendDate;
        this.comments = comments;
        this.messageState = messageState;
        this.messageFiles = messageFiles;
    }

    public void updateMessageState(MessageState messageState){
        this.messageState = messageState;
    }

    //자기 자신한테는 메시지 못보냄 (valid 처리 메소드 필요)
    private void validatedAuthorityOfMessageCreation(PmsUser sender, PmsUser receiver){
        if (sender.hasSameId(receiver.getId())){
            throw new MessageException("자기 자신에게는 메시지를 보낼 수 없습니다.");
        }
    }
}
