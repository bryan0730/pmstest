package com.springcloud.webclients.api.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum DuplicateConfirmation {

    DUPLICATE((num) -> num!=0, "등록된 조직입니다."),
    SUCCESS((num) -> num==0, "")
    ;

    private final Predicate<Long> formula;
    private final String comment;

    DuplicateConfirmation(Predicate<Long> formula, String comment){
        this.formula = formula;
        this.comment = comment;
    }

    public static DuplicateConfirmation valueOf(Long rowCount){
        return Arrays.stream(DuplicateConfirmation.values())
                .filter(c -> c.formula.test(rowCount))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("조직명 중복체크 예외발생"));
    }
}
