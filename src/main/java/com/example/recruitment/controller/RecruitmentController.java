package com.example.recruitment.controller;

import com.example.recruitment.dto.RecruitmentDto;
import com.example.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @PostMapping("/post")
    public void postingRecruitment(@RequestBody RecruitmentDto.Request request) {
        recruitmentService.postingRecruitment(request);
    }

    @GetMapping("")
    public List<RecruitmentDto.Response> getRecruitmentList() {
        return recruitmentService.getRecruitmentList();
    }

    @GetMapping("/{id}")
    public RecruitmentDto.Response getRecruitment(@PathVariable(name = "id") Long recruitId) {
        return recruitmentService.getRecruitment(recruitId);
    }

    @PutMapping("/{id}")
    public RecruitmentDto.Response modifyRecruitment(@PathVariable(name = "id") Long recruitId,
                                                     @RequestBody RecruitmentDto.Request request) {
        return recruitmentService.modifyRecruitmet(recruitId, request);
    }
}
