package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.service.MessageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageFileController {

    private final MessageFileService messageFileService;

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable(name = "fileId") Long fileId) throws MalformedURLException {

        return messageFileService.findByMessageFileId(fileId);
    }
}
