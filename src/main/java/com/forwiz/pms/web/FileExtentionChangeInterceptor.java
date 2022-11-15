package com.forwiz.pms.web;

import com.forwiz.pms.domain.message.dto.MessageFileInfoResponse;
import com.forwiz.pms.domain.message.service.MessageFileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileExtentionChangeInterceptor implements HandlerInterceptor {

    private final MessageFileStore messageFileStore;
    private static final String FILE_INFO = "msgFileInfo";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        MessageFileInfoResponse msgFileInfo = (MessageFileInfoResponse) request.getAttribute(FILE_INFO);
        String originExtFullName = msgFileInfo.getOriginExtFileName();
        String storeFileName = msgFileInfo.getStoreFileName();

        File serverFile = new File(messageFileStore.getFullPath(originExtFullName));

        serverFile.renameTo(new File(messageFileStore.getFullPath(storeFileName)));//C:/study/file/test.t
    }
}
