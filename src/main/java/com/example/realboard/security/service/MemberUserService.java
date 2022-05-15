package com.example.realboard.security.service;

import com.example.realboard.entity.Member;
import com.example.realboard.repository.MemberRepository;
import com.example.realboard.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override                           //username이 Member에서는 email을 의미
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("MemberUserService loadUserByUsername " + username);

        //email을 이용해 memberRepository의 findByEmail 호출
        Optional<Member> result = memberRepository.findByEmail(username);

        if(result.isEmpty()){   //사용자가 존재하지않으면 Exception처리
            throw new UsernameNotFoundException("Please Check Email");
        }

        Member member = result.get();

        log.info("member = " + member);

        //Member를 UserDetails 타입으로 처리하기 위해 MemberDTO 타입으로 변환
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
        );

        log.info("AuthMemberDTO : " + authMemberDTO);

        authMemberDTO.setEmail(member.getEmail());
        //MemberRole은 스프링 시큐리티에서 사용하는 SimpleGranted...로 변환, "ROLE_" 이라는 접두어를 추가해서 사용

        return authMemberDTO;

    }
}
