package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageDetailResponse;
import com.forwiz.pms.domain.message.dto.MessageReceiveListResponse;
import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.dto.MessageSendListResponse;
import com.forwiz.pms.domain.message.service.MessageService;

import com.forwiz.pms.domain.page.PageCalculator;
import com.forwiz.pms.domain.page.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pms/message")
public class MessageController {

    private final MessageService messageService;
    private final PageCalculator pageCalculator;

    @GetMapping({"/receive/{pageNum}", "/receive"})
    public String receiveMessageForm(@PathVariable(required = false) Optional<Integer> pageNum, Model model){

        int page = pageNum.isEmpty() ? 1 : pageNum.get();

        List<MessageReceiveListResponse> receiveList = messageService.findByReceiver();

        int totalRecordCount =receiveList.size();
        Paging paging = pageCalculator.calculate(totalRecordCount,10,page,5);

        List<MessageReceiveListResponse> receivePageList = receiveList.stream()
                .skip(paging.getCurrentRecordStart() - 1)
                .limit(paging.getPageRecordCount())
                .collect(Collectors.toList());

        model.addAttribute("receiveList", receivePageList);
        model.addAttribute("paging", paging);

        return "message-receive";
    }
    @GetMapping({"/send/{pageNum}", "/send"})
    public String sendMessageForm(@PathVariable(required = false) Optional<Integer> pageNum, Model model){

        int page = pageNum.isEmpty() ? 1 : pageNum.get();

        List<MessageSendListResponse> sendList = messageService.findBySender();

        int totalRecordCount = sendList.size();
        Paging paging = pageCalculator.calculate(totalRecordCount,10,page,5);
        List<MessageSendListResponse> sendPageList = sendList.stream()
                .skip(paging.getCurrentRecordStart() - 1)
                .limit(paging.getPageRecordCount())
                .collect(Collectors.toList());

        model.addAttribute("sendList", sendPageList);
        model.addAttribute("paging", paging);

        return "message-send";
    }

    @ResponseBody
    @PostMapping("/send")
    public String saveMessage(@ModelAttribute @Valid MessageSaveRequest messageSaveRequest) throws IOException {

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
