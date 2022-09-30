package com.example.realboard.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String email;

    private String phone;

    private String password;

    private String nickname;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }


    public void changePhone(String phone){
        this.phone = phone;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changePassword(String password){
        this.password = password;
    }


    public Member update(String nickname,String phone){
        this.nickname = nickname;
        this.phone = phone;

        return this;

    }

}
