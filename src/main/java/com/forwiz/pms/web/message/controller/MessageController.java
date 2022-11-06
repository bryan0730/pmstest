package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MessageController {

    @ResponseBody
    @PostMapping("/pms/message/send")
    public String saveMessage(MessageSaveRequest messageSaveRequest){
        log.info("saveMessage log");

        return "ok";
    }
}
