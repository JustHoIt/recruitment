package com.example.recruitment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String name;
    private String loginId;


    @Builder
    Member(
            String name,
            String loginId
    ) {
        this.name = name;
        this.loginId = loginId;
    }

}
