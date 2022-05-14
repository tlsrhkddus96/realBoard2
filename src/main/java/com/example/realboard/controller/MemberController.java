package com.example.realboard.controller;

import com.example.realboard.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/register")
    public void registerMember(){

        log.info(" RegisterMember Get");

    }

    @GetMapping("/login")
    public void login(){

        log.info("dsadsadsa");

    }

}
