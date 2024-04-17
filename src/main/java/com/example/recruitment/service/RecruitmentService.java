package com.example.recruitment.service;

import com.example.recruitment.dto.ApplicantInfo;
import com.example.recruitment.dto.ApplicationDTO;
import com.example.recruitment.dto.RecruitmentDTO;
import com.example.recruitment.entity.*;
import com.example.recruitment.enums.ApplicationStatus;
import com.example.recruitment.enums.EducationLevel;
import com.example.recruitment.enums.RecruitmentStatus;
import com.example.recruitment.repository.ApplicationRepository;
import com.example.recruitment.repository.CompanyMemberRepository;
import com.example.recruitment.repository.RecruitmentRepository;
import com.example.recruitment.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final ResumeRepository resumeRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void postingRecruitment(RecruitmentDTO.Request request) {
        CompanyMember companyMember = companyMemberRepository.findByLoginId(request.companyMemberId())
                .orElseThrow(() -> new RuntimeException("기업 회원 정보 없음"));

        Recruitment recruitment = request.toEntity();
        recruitment.setCompanyMember(companyMember);
        recruitment.opening();

        recruitmentRepository.save(recruitment);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentDTO.Response> getRecruitmentList() {
        List<Recruitment> recruitments = recruitmentRepository.findAllByStatus(RecruitmentStatus.OPEN);
        return recruitments.stream().map(Recruitment::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RecruitmentDTO.Response getRecruitment(Long recruitId) {
        return recruitmentRepository.findById(recruitId)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 공고입니다.")).toDto();
    }

    @Transactional
    public RecruitmentDTO.Response modifyRecruitmet(Long recruitId, RecruitmentDTO.Request request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitId)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 공고입니다."));

        if (!Objects.equals(recruitment.getCompanyMember().getLoginId(), request.companyMemberId())) {
            throw new RuntimeException("해당 공고 작성자와 일치하지 않습니다.");
        }

        return recruitment.update(request).toDto();
    }

    @Transactional
    public void deleteRecruitment(Long recruitId, RecruitmentDTO.Request request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitId)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 공고입니다."));

        // 파라미터인 DTO의 request가 아닌 토큰 값을 이용해서 예외처리를 하나 이번에는 회원정보가 더미데이터이기때문에 임시방편으로 구현
        if (!Objects.equals(recruitment.getCompanyMember().getLoginId(), request.companyMemberId())) {
            throw new RuntimeException("해당 공고 작성자와 일치하지 않습니다.");
        }

        recruitmentRepository.deleteById(recruitId);
    }

    @Transactional
    public void applyRecruitment(Long recruitId, ApplicationDTO.Request request) {

        Resume resume = resumeRepository.findByIdAndMemberId(request.resumeId(), request.memberId())
                .orElseThrow(() -> new RuntimeException("이력서 정보를 찾을 수 없습니다."));

        Recruitment recruitment = recruitmentRepository.findByIdAndStatus(recruitId, RecruitmentStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("해당하는 공고 없음"));


        Application application = Application.builder().recruitment(recruitment).resume(resume)
                .status(ApplicationStatus.APPLY_FINISHED).build();

        applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDTO.Response> getApplications(Long recruitmentId, Long companyMemberId) {
        CompanyMember companyMember = companyMemberRepository.findById(companyMemberId)
                .orElseThrow(() -> new RuntimeException("기업 회원 정보 없음"));
        List<Application> applicationList = applicationRepository.findAllByRecruitmentId(recruitmentId);

        return applicationList.stream().map(a -> ApplicationDTO.Response.builder()
                .applicationId(a.getId())
                .status(a.getStatus())
                .appliedDate(a.getAppliedDate())
                .resumeId(a.getResume().getId())
                .resumeTitle(a.getResume().getTitle())
                .educationList(a.getResume().getEducationList())
                .name(a.getResume().getMember().getName())
                .build()).toList();
    }

    @Transactional
    public void finishedRecruitment(Long recruitmentId, Long companyMemberId) {
        Recruitment recruitment = recruitmentRepository
                .findByIdAndStatusAndCompanyMemberId(recruitmentId, RecruitmentStatus.OPEN, companyMemberId)
                .orElseThrow(() -> new RuntimeException("공고 정보 없음"));

        recruitment.closing();

        List<Application> applicationList = recruitment.getApplicationList();

        //
        List<ApplicantInfo> applicantInfoList = applicationList.stream().map(Application::getResume).map(r -> {
            Education education = r.getEducationList().stream().max(Comparator.comparing(Education::getDegree))
                    .orElse(Education.builder().build());

            return new ApplicantInfo(r.getId(), EducationLevel.findByCode(education.getCode()).getScore(education.getDegree()));
        }).sorted(Comparator.comparing(ApplicantInfo::getScore).reversed()).toList();

        Map<Long, Integer> applicantInfoMap = IntStream.range(0, applicantInfoList.size()).boxed()
                .collect(Collectors.toMap(i -> applicantInfoList.get(i).getResumeId(), i -> i));

        applicationList.forEach(application -> {
            if (applicantInfoMap.get(application.getResume().getId()) < recruitment.getRecruiterCount()) {
                application.pass();
            } else {
                application.fail();
            }
        });
    }
}
