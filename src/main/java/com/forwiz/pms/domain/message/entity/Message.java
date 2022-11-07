package com.forwiz.pms.domain.message.entity;

import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;

    @Column
    private String comments;
}
