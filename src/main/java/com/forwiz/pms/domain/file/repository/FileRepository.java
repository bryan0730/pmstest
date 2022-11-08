package com.forwiz.pms.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forwiz.pms.domain.file.entity.FileInfo;

public interface FileRepository extends JpaRepository<FileInfo, Long>{

}
