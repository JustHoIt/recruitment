package com.example.recruitment.service;

import com.example.recruitment.dto.RecruitmentDto;
import com.example.recruitment.entity.CompanyMember;
import com.example.recruitment.entity.Recruitment;
import com.example.recruitment.enums.RecruitmentStatus;
import com.example.recruitment.repository.CompanyMemberRepository;
import com.example.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyMemberRepository companyMemberRepository;

    @Transactional
    public void postingRecruitment(RecruitmentDto.Request request) {
        CompanyMember companyMember = companyMemberRepository.findByLoginId(request.companyMemberId())
                .orElseThrow(() -> new RuntimeException("기업 회원 정보 없음"));

        Recruitment recruitment = request.toEntity();
        recruitment.setCompanyMember(companyMember);
        recruitment.opening();

        recruitmentRepository.save(recruitment);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentDto.Response> getRecruitmentList() {
        List<Recruitment> recruitments = recruitmentRepository.findAllByStatus(RecruitmentStatus.OPEN);
        return recruitments.stream().map(Recruitment::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RecruitmentDto.Response getRecruitment(Long recruitId) {
        return recruitmentRepository.findById(recruitId)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 공고입니다.")).toDto();
    }

    @Transactional
    public RecruitmentDto.Response modifyRecruitmet(Long recruitId, RecruitmentDto.Request request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitId)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 공고입니다."));

        if (!Objects.equals(recruitment.getCompanyMember().getLoginId(), request.companyMemberId())) {
            throw new RuntimeException("해당 공고 작성자와 일치하지 않습니다.");
        }

        return recruitment.update(request).toDto();
    }
}
