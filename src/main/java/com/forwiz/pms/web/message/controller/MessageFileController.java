package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageFileInfoResponse;
import com.forwiz.pms.domain.message.service.MessageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageFileController {

    private final MessageFileService messageFileService;

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable(name = "fileId") Long fileId, HttpServletRequest request) throws MalformedURLException {

        MessageFileInfoResponse responseInfo = messageFileService.messageFileDownload(fileId);

        request.setAttribute("msgFileInfo", responseInfo);

        String encodedUploadFileName = UriUtils.encode(responseInfo.getUploadFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(responseInfo.getResource());
    }
}
