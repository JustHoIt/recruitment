package com.example.recruitment.entity;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Education {

    private String school;
    private Integer code; // k 대학교 : 0, y 대학교 : 1,
    private Integer degree; //학위 (고졸 : 0, 대졸 : 1, 석사 : 2, 박사 : 3)으로 관리
}
