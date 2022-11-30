package com.forwiz.pms.domain.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PageCalculator {

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public Paging calculate(int totalRecordCount, int pageRecordCount, int currentPage, int pageSize){

        Paging paging = new Paging(pageRecordCount, currentPage, pageSize);

        paging.setTotalRecordCount(totalRecordCount);
        paging.setTotalPageCount((int) Math.ceil((double)totalRecordCount/(double)paging.getPageRecordCount()));

        if(paging.getTotalPageCount()<pageSize) paging.setPageSize(paging.getTotalPageCount());

        paging.setCurrentRecordStart(
                (paging.getCurrentPage()*paging.getPageRecordCount())-paging.getPageRecordCount()+1
        );

        if (paging.getCurrentPage() * paging.getPageRecordCount()<=totalRecordCount){
            paging.setCurrentRecordEnd(paging.getCurrentPage() * paging.getPageRecordCount());
        }else {
            paging.setCurrentRecordEnd(paging.getCurrentPage() * paging.getPageRecordCount()-totalRecordCount);
        }


        if ((int) Math.ceil((double)paging.getCurrentPage()/(double)paging.getPageSize())*paging.getPageSize()<=paging.getTotalPageCount()){
            paging.setCurrentPageEndNumber(
                    (int) Math.ceil((double)paging.getCurrentPage()/(double)paging.getPageSize())*paging.getPageSize()
            );
            paging.setCurrentPageStartNumber(paging.getCurrentPageEndNumber()-(paging.getPageSize()-1));
        }else {
            paging.setCurrentPageEndNumber(paging.getTotalPageCount());
            paging.setCurrentPageStartNumber(
                    (paging.getPageSize()*(int)Math.ceil(paging.getTotalPageCount()/paging.getPageSize()))+1
            );
        }

        paging.setHasNextPage(paging.getTotalPageCount() > paging.getCurrentPageEndNumber());

        paging.setHasPreviousPage(paging.getCurrentPageStartNumber() > 1);

        return paging;
    }
}
