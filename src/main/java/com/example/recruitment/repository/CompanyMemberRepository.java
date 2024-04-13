package com.example.recruitment.repository;

import com.example.recruitment.entity.CompanyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
    Optional<CompanyMember> findByLoginId(String loginId);
}
