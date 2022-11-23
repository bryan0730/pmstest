package com.forwiz.pms.domain.file.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.forwiz.pms.domain.board.dto.BoardFileRequestDto;
import com.forwiz.pms.domain.board.dto.BoardRequestDto;
import com.forwiz.pms.domain.board.entity.BoardFile;
import com.forwiz.pms.domain.board.repository.BoardFileRepository;
import com.forwiz.pms.domain.file.dto.FileInfoDto;
import com.forwiz.pms.domain.file.entity.FileInfo;
import com.forwiz.pms.domain.file.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	/*
	 * upload: path
	 */
	@Value("${upload.path}")
	private String uploadDir;

	private final FileRepository fileRepository;

    private final BoardFileRepository boardFileRepository;
	/**
	 * @Method : saveFile
	 */
	@Transactional
	public Map<String, Object> saveFile(BoardRequestDto boardRequestDto, Long boardId) throws Exception {
		
		List<MultipartFile> multipartFile = boardRequestDto.getMultipartFile();

		//결과 map
		Map<String, Object> result = new HashMap<String, Object>();

		//파일 시퀀스 list
		List<Long> fileIds = new ArrayList<Long>();


        try {
            if (multipartFile != null) {
                if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
                    for (MultipartFile file1 : multipartFile) {
                        String originalFileName = file1.getOriginalFilename();    //오리지날 파일명
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
//                        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명
                        String savedFileName = UUID.randomUUID() + extension.substring(0,3);    //저장될 파일 명(확장자 깨트리기)
                        File targetFile = new File(uploadDir + savedFileName);

                        
                        //초기값으로 fail 설정
                        result.put("result", "FAIL");

                        FileInfoDto fileDto = FileInfoDto.builder()
                                .originFileName(originalFileName)
                                .savedFileName(savedFileName)
                                .uploadDir(uploadDir)
                                .extension(extension)
                                .fileVolume(file1.getSize())
                                .contentType(file1.getContentType())
                                .build();
                        
						// insert
						FileInfo file = fileDto.toEntity();
						Long fileId = insertFile(file);

						try {
							InputStream fileStream = file1.getInputStream();
							FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일저장
							
							//배열에 담기
							fileIds.add(fileId);
							result.put("fileIdxs", fileIds.toString());
							result.put("result", "OK");

							
						} catch (Exception e) {
							//파일삭제
							FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
							e.printStackTrace();
							result.put("result", "FAIL");
							break;

						}
						BoardFileRequestDto boardFileRequestDto = BoardFileRequestDto.builder()
								.boardId(boardId)
								.build();
						BoardFile boardFile = boardFileRequestDto.toEntity(file);
						insertBoardFile(boardFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	//파일 저장 db
	/**
	 * @Method : insertFile
	 */
	@Transactional
	private Long insertFile(FileInfo file) {
		return fileRepository.save(file).getId();
	}

	/**
	 * @Method : insertBoardFile
	 */
	@Transactional
	private Long insertBoardFile(BoardFile boardFile) {
		return boardFileRepository.save(boardFile).getId();
	}
	
	
    /**
     * @Method : deleteBoardFile
     */
    @Transactional
    public BoardFile deleteBoardFile(Long boardFileId){
        BoardFile boardFile = boardFileRepository.findById(boardFileId).get();

        //삭제
        boardFile.delete("Y");
        return boardFile;
    }


	/**
	 * @Method : getFile
	 */
	public FileInfoDto getFile(Long fileIdx) {
		FileInfo fileInfo = fileRepository.findById(fileIdx).get();
		return new FileInfoDto(fileInfo);
	}
    
    
}
