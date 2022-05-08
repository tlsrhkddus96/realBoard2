package com.example.realboard.controller;

import com.example.realboard.Service.BoardService;
import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @GetMapping("/register")
    public void register(){

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
    public void read(Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("Bno : " + bno);

        BoardDTO boardDTO = boardService.getBoard(bno);

        model.addAttribute("dto",boardDTO);

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

        boardService.modifyBoardTest(boardDTO);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("bno",boardDTO.getBno());

        return "redirect:/board/read";

    }

}
