package com.example.recruitment.repository;

import com.example.recruitment.entity.Recruitment;
import com.example.recruitment.enums.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    List<Recruitment> findAllByStatus(RecruitmentStatus status);

    Optional<Recruitment> findByIdAndStatus(Long recruitmentId, RecruitmentStatus status);

    Optional<Recruitment> findByIdAndStatusAndCompanyMemberId(Long recruitmentId, RecruitmentStatus recruitmentStatus, Long companyMemberId);
}
