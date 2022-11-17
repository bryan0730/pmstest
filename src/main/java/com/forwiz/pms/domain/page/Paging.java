package com.forwiz.pms.domain.page;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Paging {
    private int totalPageCount; //전체 페이지 개수
    private int totalRecordCount; //전체 요소 개수

    private int pageRecordCount; //페이지 당 보여줄 요소 개수(사용자 입력)
    private int currentPage; //현재 페이지 번호(초기화 1?)
    private int pageSize; //한번에 보여줄 페이지 사이즈(사용자 입력)
    private int currentPageStartNumber;
    private int currentPageEndNumber;


    private boolean hasNextPage;
    private boolean hasPreviousPage;

    private int currentRecordStart;
    private int currentRecordEnd;

    @Builder
    public Paging(int pageRecordCount, int currentPage, int pageSize){
        this.pageRecordCount = pageRecordCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

}
