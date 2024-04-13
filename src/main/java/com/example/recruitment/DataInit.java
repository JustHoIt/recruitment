package com.example.recruitment;

import com.example.recruitment.entity.CompanyMember;
import com.example.recruitment.entity.Member;
import com.example.recruitment.repository.CompanyMemberRepository;
import com.example.recruitment.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final MemberRepository memberRepository;
    private final CompanyMemberRepository companyMemberRepository;

    @PostConstruct
    void init() {
        List<Member> memberList = new ArrayList<>();
        List<CompanyMember> companyMemberList = new ArrayList<>();
        IntStream.range(1, 101).forEach(i -> {
            memberList.add(Member.builder().name("개인회원" + i).loginId("test" + i).build());
            companyMemberList.add(CompanyMember.builder().companyName("기업회원" + i).loginId("company" + i).build());
        });

        memberRepository.saveAll(memberList);
        companyMemberRepository.saveAll(companyMemberList);
    }

}
