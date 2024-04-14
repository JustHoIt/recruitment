package com.example.recruitment.service;

import com.example.recruitment.dto.ResumeDTO;
import com.example.recruitment.entity.Member;
import com.example.recruitment.entity.Resume;
import com.example.recruitment.repository.MemberRepository;
import com.example.recruitment.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void postingResume(ResumeDTO.Request request) {
        Member member = memberRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new RuntimeException("일치하는 회원정보 없음"));

        Resume resume = request.toEntity();
        resume.open();
        resume.setMember(member);
        resumeRepository.save(resume);
    }
}
