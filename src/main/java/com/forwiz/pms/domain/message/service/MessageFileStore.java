package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.entity.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MessageFileStore {

    @Value("${upload.path}")
    private String fileDir;

    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<UploadFile> storeFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                storeFileList.add(storeFile(multipartFile));
            }
        }

        return storeFileList;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()){
            return null;
        }
        String originFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFileName);
        String ext = extractExt(originFileName);
        Long volume = multipartFile.getSize();
        String contentType = multipartFile.getContentType();

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return UploadFile.builder()
                .uploadFileName(originFileName)
                .storeFileName(storeFileName)
                .ext(ext)
                .volume(volume)
                .contentType(contentType)
                .build();
    }

    private String createStoreFileName(String originFileName){
        String ext = extractExt(originFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originFileName){
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos+1);
    }
}
