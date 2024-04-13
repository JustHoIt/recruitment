package com.example.recruitment.entity;

import com.example.recruitment.dto.RecruitmentDto;
import com.example.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    @Builder
    public Recruitment(
            String title,  // 공고명
            Integer recruiterCount, //모집 인원
            String description, //공고 설명
            LocalDateTime closingDate //채용 종료일
    ) {
        this.title = title;
        this.recruiterCount = recruiterCount;
        this.description = description;
        this.closingDate = closingDate;
    }

    public void opening() {
        this.status = RecruitmentStatus.OPEN;
    }

    public RecruitmentDto.Response toDto() {
        return RecruitmentDto.Response.builder()
                .recruitmentId(this.id)
                .title(this.title)
                .recruiterCount(this.recruiterCount)
                .description(this.description)
                .status(this.status)
                .modifyDate(this.modifyDate)
                .postingDate(this.postingDate)
                .closingDate(this.closingDate)
                .companyMemberId(this.companyMember.getId())
                .companyName(this.companyMember.getCompanyName())
                .build();
    }
}
