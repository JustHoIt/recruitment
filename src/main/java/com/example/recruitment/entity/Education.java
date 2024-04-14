package com.example.recruitment.entity;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Education {

    private String school;
    private Integer degree; //학위 (고졸 : 0, 대졸 : 1, 석사 : 2, 박사 : 3)으로 관리
}
