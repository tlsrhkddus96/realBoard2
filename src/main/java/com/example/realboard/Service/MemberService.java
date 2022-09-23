package com.example.realboard.Service;

import com.example.realboard.entity.Member;
import com.example.realboard.dto.MemberDTO;

public interface MemberService {


    String register(MemberDTO memberDTO) throws Exception;

    MemberDTO getMember(String email) throws Exception;

    void modify(MemberDTO memberDTO) throws Exception;

    void remove(MemberDTO memberDTO);

    String checkNicknameEmail(String nickname, String email);

    String changePwd(MemberDTO memberDTO);



    default Member dtoToEntity(MemberDTO memberDTO){

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .nickname(memberDTO.getNickname())
                .phone(memberDTO.getPhone())
                .password(memberDTO.getPassword())
                .build();

        return member;

    }

    default MemberDTO entityToDto(Member member){

        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .password(member.getPassword())
                .build();

        return memberDTO;

    }


}
