package com.example.realboard.controller;

import com.example.realboard.Service.MemberService;
import com.example.realboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@Log4j2
@RequestMapping("/memberRest/")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/checkNicknameEmail")
    public ResponseEntity<String> checkNicknameEmail(@RequestBody MemberDTO memberDTO){

        String nickname = memberDTO.getNickname();
        String email = memberDTO.getEmail();

        String result = memberService.checkNicknameEmail(nickname,email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("/changePwd")
    public ResponseEntity<String> changePwd(@RequestBody MemberDTO memberDTO){

        String result = memberService.changePwd(memberDTO);

        return new ResponseEntity<>(result,HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody MemberDTO memberDTO) throws Exception {

        String result = memberService.register(memberDTO);

        return new ResponseEntity<>(result,HttpStatus.OK);

    }


}
