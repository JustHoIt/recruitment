package com.example.recruitment.dto;

public class ApplicationDTO {
    public record Request(
            Long memberId,
            Long resumeId
    ){
    }
}
