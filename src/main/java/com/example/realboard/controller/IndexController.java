package com.example.realboard.controller;

import com.example.realboard.Service.MemberService;
import com.example.realboard.config.auth.LoginUser;
import com.example.realboard.config.auth.dto.SessionUser;
import com.example.realboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser member) throws Exception {

        if(member != null){
           MemberDTO memberDTO =  memberService.getMember(member.getEmail());
           model.addAttribute("OAuthMemberDTO",memberDTO);
        }

        log.info( "plz "+model.getAttribute("OAuthMemberDTO"));

        return "redirect:/board/list";

    }

}
