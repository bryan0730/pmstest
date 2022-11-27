package com.forwiz.pms.domain.rank.exception;

public class NotSaveRankException extends RuntimeException{

    private Long orgId;
    public NotSaveRankException(String msg, Long orgId){
        super(msg);
        this.orgId = orgId;
    }

    public Long getOrgId() {
        return orgId;
    }
}
