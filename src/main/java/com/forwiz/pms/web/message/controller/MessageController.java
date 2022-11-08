package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageReceiveListResponse;
import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.service.MessageService;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pms/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public String messageForm(Model model){

        PmsUserDetails principal =
                (PmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long loginUserId = principal.getPmsUser().getId();
        List<MessageReceiveListResponse> receiveList = messageService.findByReceiver(loginUserId);

        return "message-receive";
    }

    @ResponseBody
    @PostMapping("/send")
    public String saveMessage(@RequestBody MessageSaveRequest messageSaveRequest){

        messageService.saveMessage(messageSaveRequest);

        return "메시지 전송하였습니다.";
    }
}
