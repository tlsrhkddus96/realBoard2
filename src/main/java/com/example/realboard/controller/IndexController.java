package com.example.realboard.controller;

import com.example.realboard.Service.MemberService;
import com.example.realboard.config.auth.dto.SessionUser;
import com.example.realboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequiredArgsConstructor
public class IndexController {

    private final HttpSession httpSession;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) throws Exception {

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if(member != null){
           MemberDTO memberDTO =  memberService.getMember(member.getEmail());
           model.addAttribute("googleMemberDTO",memberDTO);
        }

        log.info( "plz "+model.getAttribute("googleMemberDTO"));

        return "redirect:/board/list";

    }

}
