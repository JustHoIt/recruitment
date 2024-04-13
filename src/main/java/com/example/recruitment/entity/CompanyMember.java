package com.example.recruitment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_member_id")
    private long id;
    private String companyName;
    private String loginId;


    @Builder
    CompanyMember(
            String companyName,
            String loginId
    ) {
        this.companyName = companyName;
        this.loginId = loginId;
    }

}
