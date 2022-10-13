package com.springcloud.webclients.api.util;

import java.util.Arrays;

public enum ErrorCode {

    BAD_CREDENTIAL("L001", "BadCredentialsException", "비밀번호가 일치하지 않습니다."),
    INTERNAL_AUTHENTICATION("L002", "InternalAuthenticationServiceException", "아이디를 확인하세요."),
    AUTHENTICATION_NOT_FOUND("L003", "AuthenticationCredentialNotFoundException", "인증이 거부되었습니다."),
    LOCKED("L004", "LockedEception", "잠긴 계정입니다."),
    DISABLED("L005", "DisabledException","계정이 비활성화 상태입니다."),
    ACCOUNT_EXPIRED("L006", "AccountExpiredException", "계정 유효기간이 만료되었습니다."),
    CREDENTIAL_EXPIRED("L007", "CredentialExpiredException", "비밀번호 유효기간이 만료되었습니다."),
    FAIL("L007", "IdontKnow", "알 수 없는 예외입니다.")
    ;

    private String code;
    private String className;
    private String errMsg;

    ErrorCode(final String code, final String className, final String errMsg){
        this.code = code;
        this.className = className;
        this.errMsg = errMsg;
    }

    public String getCode(){
        return this.code;
    }

    public String getClassName(){
        return this.className;
    }

    public String getErrMsg(){
        return this.errMsg;
    }

    public static ErrorCode getErrorCode(String className){

        return Arrays.stream(values())
                .filter(s -> s.className.equals(className))
                .findFirst()
                .orElse(FAIL)
                ;
    }
}
