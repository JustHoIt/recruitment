package com.example.recruitment.entity;

import com.example.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private long id;    //고유 식별 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_member_id")
    private CompanyMember companyMember; // 기업회원 아이디
    private String title;   // 공고명
    private Integer recruiterCount; //모집 인원
    private String description; //공고 설명
    @Enumerated(EnumType.STRING)
    private RecruitmentStatus status;    //공고 상태(enum)
    private LocalDateTime closingDate; //채용 종료일
    @UpdateTimestamp
    private LocalDateTime modifyDate; //채용 수정일
    @CreationTimestamp
    private LocalDateTime postingDate; //채용 공고일


}
