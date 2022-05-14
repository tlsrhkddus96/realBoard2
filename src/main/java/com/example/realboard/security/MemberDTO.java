package com.example.realboard.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private Long mid;

    private String email;

    private String nickname;



    public MemberDTO(String username, String password,
                     Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.email = username; //일단 pk는 아닌데 흠..(mid)

    }
}
