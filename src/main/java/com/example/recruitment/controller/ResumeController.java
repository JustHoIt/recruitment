package com.example.recruitment.controller;

import com.example.recruitment.dto.ResumeDTO;
import com.example.recruitment.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;


    @PostMapping("/post")
    public void postingResume(@RequestBody ResumeDTO.Request request) {
        resumeService.postingResume(request);
    }

}
