package com.example.recruitment.entity;

import com.example.recruitment.enums.ResumeStatus;
import com.example.recruitment.utils.EducationListJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private long resumeId; //고유 식별 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //멤버 ID

    private String title; //이력서 제목
    private String description; //이력서 설명
    @Convert(converter = EducationListJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Education> educationList; //학력
    @Enumerated(EnumType.STRING)
    private ResumeStatus Status; //이력서 상태
    @UpdateTimestamp
    private LocalDateTime modifyDate; //채용 수정일
    @CreationTimestamp
    private LocalDateTime postingDate; //채용 공고일

}
