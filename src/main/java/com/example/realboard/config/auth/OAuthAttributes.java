package com.example.realboard.config.auth;

import com.example.realboard.encrypt.Encrypt;
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
    private String phone;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String phone){

        this.attributes=attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String,Object> attributes) throws Exception {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName , Map<String,Object> attributes) throws Exception {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .phone(Encrypt.encryptAES256("Google"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public Member toEntity() throws Exception {

        Member member = Member.builder()
                .nickname(name)
                .email(email)
                .phone(Encrypt.encryptAES256("Google"))
                .build();

        member.addMemberRole(MemberRole.USER);

         return member;

    }


}
