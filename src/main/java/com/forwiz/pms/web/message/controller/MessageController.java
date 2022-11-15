package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageDetailResponse;
import com.forwiz.pms.domain.message.dto.MessageReceiveListResponse;
import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.dto.MessageSendListResponse;
import com.forwiz.pms.domain.message.entity.UploadFile;
import com.forwiz.pms.domain.message.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pms/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/receive")
    public String receiveMessageForm(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<MessageReceiveListResponse> receiveList = messageService.findByReceiver(authentication);
        model.addAttribute("receiveList", receiveList);

        return "message-receive";
    }
    @GetMapping("/send")
    public String sendMessageForm(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<MessageSendListResponse> sendList = messageService.findBySender(authentication);
        model.addAttribute("sendList", sendList);

        return "message-send";
    }

    @ResponseBody
    @PostMapping("/send")
    public String saveMessage(@ModelAttribute @Valid MessageSaveRequest messageSaveRequest) throws IOException {

//        messageSaveRequest.getMessageFiles().forEach(a -> System.out.println(a.getOriginalFilename()));

        messageService.saveMessage(messageSaveRequest);

        return "메시지 전송하였습니다.";
    }
    @GetMapping("/{messageId}")
    public String messageDetailsForm(@PathVariable Long messageId, Model model){

        MessageDetailResponse response = messageService.findByMessageId(messageId);
        model.addAttribute("messageDetails", response);

        return "message-details";
    }
}
