package com.example.recruitment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_record_id")
    private long id;
    private String status;
    @CreationTimestamp
    private LocalDateTime appliedDate; //지원 일자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiment_id")
    private Recruitment recruitment; //채용공고 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume; //이력서 id
}
