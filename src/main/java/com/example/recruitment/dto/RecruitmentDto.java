package com.example.recruitment.dto;


import com.example.recruitment.entity.Recruitment;
import com.example.recruitment.enums.RecruitmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class RecruitmentDto {
    public record Request(
            String title,  // 공고명
            Integer recruiterCount, //모집 인원
            String description, //공고 설명
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime closingDate, //채용 종료일
            String companyMemberId
    ) {
        public Recruitment toEntity() {
            return Recruitment.builder()
                    .title(title)
                    .recruiterCount(recruiterCount)
                    .description(description)
                    .closingDate(closingDate)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long recruitmentId;
        private String title;
        private Integer recruiterCount;
        private String description;
        private RecruitmentStatus status;
        private LocalDateTime closingDate;
        private LocalDateTime modifyDate;
        private LocalDateTime postingDate;
        private Long companyMemberId;
        private String companyName;
    }
}
