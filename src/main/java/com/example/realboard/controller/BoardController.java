package com.example.realboard.controller;

import com.example.realboard.Service.BoardService;
import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.MemberDTO;
import com.example.realboard.dto.PageRequestDTO;
import com.example.realboard.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void register(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model){

        log.info("MemberDTO auth : " + authMemberDTO);

        model.addAttribute("dto",authMemberDTO);

    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        log.info("BoardDTO : " + boardDTO);


        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";

    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("pageRequestDTO : " + pageRequestDTO);

        model.addAttribute("result",boardService.getList(pageRequestDTO));

    }

    @GetMapping({"/read","/modify"})
    public void read(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
            Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("AuthMemberDTO : " + authMemberDTO);

        log.info("Bno : " + bno);

        BoardDTO boardDTO = boardService.getBoard(bno);

        model.addAttribute("dto",boardDTO);
        model.addAttribute("authDTO",authMemberDTO);

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){

        log.info("bno : " + bno);

        boardService.removeBoard(bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";

    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, @ModelAttribute("requestDTO")PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        log.info("boardDTO : " + boardDTO);

        boardService.modifyBoard(boardDTO);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("bno",boardDTO.getBno());
        
        
        /*
        *   Modify시 ref reforder step 등등 다 초기화됨 kidRegister참고해서 고쳐라sibal
        * 
        * */

        return "redirect:/board/read";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/kidRegister")
    public void kidRegister(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                            Model model,int parentNum, int ref){

        log.info("Auth MemberDTO : " + authMemberDTO);

        model.addAttribute("dto",authMemberDTO);
        model.addAttribute("parentNum",parentNum);
        model.addAttribute("ref",ref);

        log.info("ParentNum : " + parentNum);
        log.info("Ref : " + ref);


    }

    @PostMapping("/kidRegister")
    public String kidRegister(BoardDTO boardDTO,RedirectAttributes redirectAttributes){

        log.info("boardDTO : " + boardDTO);

        Long bno = boardService.kidRegister(boardDTO);
        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";

    }

}
