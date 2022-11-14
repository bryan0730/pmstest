package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.entity.MessageFile;
import com.forwiz.pms.domain.message.entity.UploadFile;
import com.forwiz.pms.domain.message.exception.NoFileDownloadAuthException;
import com.forwiz.pms.domain.message.repository.MessageFileRepository;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageFileService {

    private final MessageFileRepository messageFileRepository;
    private final MessageFileStore messageFileStore;

    @Transactional
    public void saveMessageFile(Message message, List<MultipartFile> files) throws IOException {

        List<UploadFile> storeFiles = messageFileStore.storeFiles(files);
        for (UploadFile storeFile : storeFiles) {
            MessageFile messageFile = MessageFile.builder()
                    .storeFileName(storeFile.getStoreFileName())
                    .uploadFileName(storeFile.getUploadFileName())
                    .extension(storeFile.getExt())
                    .fileVolume(storeFile.getVolume())
                    .fileContentType(storeFile.getContentType())
                    .message(message)
                    .build();

            messageFileRepository.save(messageFile);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Resource> findByMessageFileId(Long fileId) throws MalformedURLException {
        
        MessageFile messageFile = messageFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);

        Long receiverId = messageFile.getMessage().getReceiver().getId();
        Long senderId = messageFile.getMessage().getSender().getId();
        PmsUserDetails principal = (PmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loginId = principal.getPmsUser().getId();

        if(!receiverId.equals(loginId) && !senderId.equals(loginId)){
            throw new NoFileDownloadAuthException();
        }

        String storeFileName = messageFile.getStoreFileName();
        String uploadFileName = messageFile.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + messageFileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
