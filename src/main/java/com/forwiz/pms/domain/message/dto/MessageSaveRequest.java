package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.annotation.FileSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSaveRequest {

    @NotNull(message = "받는 사람은 필수입니다.")
    private Long messageReceiver;

    @NotNull(message = "보내는 사람은 필수입니다.")
    private Long messageSender;

    @NotBlank(message = "내용은 필수입니다.")
    private String comment;

    @PastOrPresent
    private Date sendDate;

    @FileSize(max = 5)
    private List<MultipartFile> messageFiles = new ArrayList<>();

}
