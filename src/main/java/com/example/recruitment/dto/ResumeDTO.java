package com.example.recruitment.dto;

import com.example.recruitment.entity.Education;
import com.example.recruitment.entity.Resume;

import java.util.List;

public class ResumeDTO {
    public record Request(
            String title,
            List<EducationDTO> educationList,
            String description,
            String loginId
    ) {
        public Resume toEntity() {
            return Resume.builder()
                    .title(title)
                    .educationList(educationList.stream().map(e -> Education.builder().school(e.school).degree(e.degree).build()).toList())
                    .description(description)
                    .build();
        }
    }

    public record EducationDTO(
            String school,
            Integer degree
    ) {

    }
}
