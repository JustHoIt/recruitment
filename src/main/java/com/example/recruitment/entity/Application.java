package com.example.recruitment.entity;

import com.example.recruitment.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @CreationTimestamp
    private LocalDateTime appliedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    Application(
            Resume resume,
            Recruitment recruitment,
            ApplicationStatus status
    ) {
        this.resume = resume;
        this.recruitment = recruitment;
        this.status = status;
    }

    public void pass() {
        this.status = ApplicationStatus.PASS;
    }

    public void fail() {
        this.status = ApplicationStatus.FAIL;
    }
}
