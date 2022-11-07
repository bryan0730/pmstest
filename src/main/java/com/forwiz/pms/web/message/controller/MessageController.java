package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pms/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public String messageForm(){
        return "message";
    }

    @ResponseBody
    @PostMapping("/send")
    public String saveMessage(@RequestBody MessageSaveRequest messageSaveRequest){

        messageService.saveMessage(messageSaveRequest);

        return "메시지 전송하였습니다.";
    }
}
