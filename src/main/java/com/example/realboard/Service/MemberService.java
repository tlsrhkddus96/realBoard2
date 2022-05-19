package com.example.realboard.Service;

import com.example.realboard.entity.Member;
import com.example.realboard.dto.MemberDTO;

public interface MemberService {


    String register(MemberDTO memberDTO);

    MemberDTO getMember(String email);

    void modify(MemberDTO memberDTO);

    void remove(MemberDTO memberDTO);



    default Member dtoToEntity(MemberDTO memberDTO){

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .nickname(memberDTO.getNickname())
                .password(memberDTO.getPassword())
                .build();

        return member;

    }

    default MemberDTO entityToDto(Member member){

        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .build();

        return memberDTO;

    }


}
