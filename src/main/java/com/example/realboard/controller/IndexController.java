package com.example.realboard.controller;

import com.example.realboard.config.auth.dto.SessionUser;
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

    @GetMapping("/")
    public String index(Model model){

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if(member != null){
            model.addAttribute("member",member.getEmail());
        }

        log.info( "plz "+model.getAttribute("member"));

        return "redirect:/board/list";

    }

}
