package com.example.realboard.config.auth;

import com.example.realboard.entity.Member;
import com.example.realboard.entity.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Getter
@Setter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,
                           String nameAttributeKey,
                           String name, String email){

        this.attributes=attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.name = name;
        this.email = email;

    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String,Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName , Map<String,Object> attributes){

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public Member toEntity(){

        Member member = Member.builder()
                .nickname(name)
                .email(email)
                .build();

        member.addMemberRole(MemberRole.USER);

         return member;

    }


}
