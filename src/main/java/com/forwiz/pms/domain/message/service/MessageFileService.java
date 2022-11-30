package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.dto.MessageFileInfoResponse;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.entity.MessageFile;
import com.forwiz.pms.domain.message.entity.UploadFile;
import com.forwiz.pms.domain.message.exception.NoFileDownloadAuthException;
import com.forwiz.pms.domain.message.repository.MessageFileRepository;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
    public MessageFileInfoResponse messageFileDownload(Long fileId) throws MalformedURLException {

        MessageFile messageFile = messageFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);

        Long receiverId = messageFile.getMessage().getReceiver().getId();
        Long senderId = messageFile.getMessage().getSender().getId();
        PmsUserDetails principal = (PmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loginId = principal.getPmsUser().getId();

        if(!receiverId.equals(loginId) && !senderId.equals(loginId)){
            throw new NoFileDownloadAuthException();
        }

        String storeFileName = messageFile.getStoreFileName(); //   test.t
        String uploadFileName = messageFile.getUploadFileName();

        int idx = storeFileName.lastIndexOf(".");
        String originFileName = storeFileName.substring(0,idx); //확장자 제거한 uuid 파일명
        String originExtFullName = originFileName+"."+messageFile.getExtension(); //원래 확장자 붙인 파일명

        File serverFile = new File(messageFileStore.getFullPath(storeFileName)); // C:/study/file/test.t
        serverFile.renameTo(new File(messageFileStore.getFullPath(originExtFullName)));// C:/study/file/test.txt

        UrlResource resource = new UrlResource("file:" + messageFileStore.getFullPath(originExtFullName));

        return new MessageFileInfoResponse(resource, originExtFullName, storeFileName, uploadFileName);
    }
}
