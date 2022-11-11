package com.forwiz.pms.web.file.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forwiz.pms.domain.file.entity.FileInfo;
import com.forwiz.pms.domain.file.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {

	private final FileRepository fileRepository;

	@GetMapping(value = {"/fileDownload/{fileIdx}"})
	@ResponseBody
	public void downloadFile(HttpServletResponse res, @PathVariable Long fileIdx) throws UnsupportedEncodingException {

		// 파일 조회
		FileInfo fileInfo = fileRepository.findById(fileIdx).get();
		// 파일 경로
		Path saveFilePath = Paths.get(fileInfo.getUploadDir() + java.io.File.separator + fileInfo.getSavedFileName());
		// 해당 경로에 파일이 없으면
		if (!saveFilePath.toFile().exists()) {	//경로를 파일객체로 변환 존재여부
			throw new RuntimeException("file not found");
		}

		// 파일 헤더 설정
		setFileHeader(res, fileInfo);

		// 파일 복사
		fileCopy(res, saveFilePath);
	}

	/**
	 * 파일 header 설정
	 */
	private void setFileHeader(HttpServletResponse res, FileInfo fileInfo) throws UnsupportedEncodingException {
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder.encode((String) fileInfo.getOriginFileName(), "UTF-8") + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");
		res.setHeader("Content-Type", "application/download; utf-8");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");
	}

	/**
	 * 파일 복사
	 */
	private void fileCopy(HttpServletResponse res, Path saveFilePath) {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(saveFilePath.toFile());
			FileCopyUtils.copy(fis, res.getOutputStream());	//복사
			res.getOutputStream().flush();	//버퍼 비우기
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}