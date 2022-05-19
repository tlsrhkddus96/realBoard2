package com.example.realboard.controller;

import com.example.realboard.Service.MemberService;
import com.example.realboard.dto.MemberDTO;
import com.example.realboard.security.AuthMemberDTO;
import com.example.realboard.security.service.MemberUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;

@Controller
@Log4j2
@RequestMapping("/member/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberUserService memberUserService;


    @GetMapping("/register")
    public void registerMember(){

        log.info(" RegisterMember Get");

    }

    @PostMapping("/register")
    public String registerMember(MemberDTO memberDTO, RedirectAttributes redirectAttributes){

        log.info("Member DTO : " + memberDTO);

        String email = memberService.register(memberDTO);

        redirectAttributes.addFlashAttribute("email", email);

        return "redirect:/board/list";


    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/my","/modify"})
    public void read(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model){
                    //@Authentication으로 로그인된 계정의 DTO 가져옴

        log.info("AuthMemberDTO : " + authMemberDTO);

        String email = authMemberDTO.getEmail();

        log.info( " email  : " + email);

        //로그인된 계정의 이메일로 findBy쿼리 진행
        MemberDTO dto =  memberService.getMember(email);

        log.info("dto : " + dto);

        //"dto" 에 데이터를 넣어주고 화면에 해당 값들을 띄움
        model.addAttribute("dto", dto);


    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(MemberDTO memberDTO){

        log.info(memberDTO);

        memberService.modify(memberDTO);

        return "redirect:/member/my";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public String remove(MemberDTO memberDTO){

        log.info(memberDTO);

        memberService.remove(memberDTO);

        return "redirect:/logout";

    }


/*    @GetMapping("/login")
    public void login(){

        log.info("MemberLogin Get");

    }


    @GetMapping("/login/success")
    public String login(AuthMemberDTO authMemberDTO){

        log.info("AuthMemberDTO : " + authMemberDTO);


        String email = authMemberDTO.getEmail();

        UserDetails a = memberUserService.loadUserByUsername(email);

        a.getAuthorities();

        return "redirect:/board/list";
    }*/

}
