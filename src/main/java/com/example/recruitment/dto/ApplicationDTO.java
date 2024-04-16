package com.example.recruitment.dto;

import com.example.recruitment.entity.Education;
import com.example.recruitment.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ApplicationDTO {
    public record Request(
            Long memberId,
            Long resumeId
    ) {
    }

    @Builder
    @Getter
    public static class Response {
        private Long applicationId;
        private ApplicationStatus status;
        private LocalDateTime appliedDate;
        private Long resumeId;
        private String resumeTitle;
        private List<Education> educationList;
        private String name;
    }

}
