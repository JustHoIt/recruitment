package com.example.recruitment.controller;

import com.example.recruitment.dto.ApplicationDTO;
import com.example.recruitment.dto.RecruitmentDTO;
import com.example.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @PostMapping("/post")
    public void postingRecruitment(@RequestBody RecruitmentDTO.Request request) {
        recruitmentService.postingRecruitment(request);
    }

    @GetMapping("")
    public List<RecruitmentDTO.Response> getRecruitmentList() {
        return recruitmentService.getRecruitmentList();
    }

    @GetMapping("/{id}")
    public RecruitmentDTO.Response getRecruitment(@PathVariable(name = "id") Long recruitId) {
        return recruitmentService.getRecruitment(recruitId);
    }

    @PutMapping("/{id}")
    public RecruitmentDTO.Response modifyRecruitment(@PathVariable(name = "id") Long recruitId,
                                                     @RequestBody RecruitmentDTO.Request request) {
        return recruitmentService.modifyRecruitmet(recruitId, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRecruitment(@PathVariable(name = "id") Long recruitId, @RequestBody RecruitmentDTO.Request request) {

        recruitmentService.deleteRecruitment(recruitId, request);
    }

    @PostMapping("/{id}/applications")
    public void applyRecruitment(@PathVariable(name = "id") Long recruitId, @RequestBody ApplicationDTO.Request request) {

        recruitmentService.applyRecruitment(recruitId, request);
    }
}
