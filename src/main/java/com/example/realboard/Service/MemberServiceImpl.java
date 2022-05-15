package com.example.realboard.Service;

import com.example.realboard.entity.Member;
import com.example.realboard.entity.MemberRole;
import com.example.realboard.repository.MemberRepository;
import com.example.realboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public String register(MemberDTO memberDTO) {

        Member member = dtoToEntity(memberDTO);

        String password = member.getPassword();

        String enCodedPassword = passwordEncoder.encode(password);

        member.setPassword(enCodedPassword);    // 인코딩된 패스워드로 변경
        member.addMemberRole(MemberRole.USER); //USER 권한 위임

        memberRepository.save(member);

        return member.getEmail();

    }

    @Override
    public MemberDTO getMember(String email) {

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        log.info(member);

        MemberDTO memberDTO = entityToDto(member);
        log.info(memberDTO);

        return memberDTO;
    }
}
