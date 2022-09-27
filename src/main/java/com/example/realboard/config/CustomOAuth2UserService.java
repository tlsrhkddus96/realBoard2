package com.example.realboard.config;

import com.example.realboard.config.auth.OAuthAttributes;
import com.example.realboard.config.auth.dto.SessionUser;
import com.example.realboard.entity.Member;
import com.example.realboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*registrationID >> Email로 변경 제발*/
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //String registrationId = delegate.loadUser(userRequest).getAttributes().get("email").toString();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes());

        log.info("Attributes : >>" + attributes.getAttributes());
        log.info("OAuthAttributes.of >>" );
        log.info(registrationId);
        log.info(userNameAttributeName);
        log.info(oAuth2User.getAttributes());
        log.info(delegate.loadUser(userRequest).getAttributes().get("email"));

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")),
        attributes.getAttributes(),
        attributes.getNameAttributeKey());

    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }

}
