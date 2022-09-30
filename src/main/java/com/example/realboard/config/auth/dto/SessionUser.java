package com.example.realboard.config.auth.dto;

import com.example.realboard.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String nickname;
    private String email;
    private String phone;

    public SessionUser(Member member){
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.phone = member.getPhone();
    }

}
