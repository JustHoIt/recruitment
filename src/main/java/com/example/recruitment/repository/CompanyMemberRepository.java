package com.example.recruitment.repository;

import com.example.recruitment.entity.CompanyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
}
