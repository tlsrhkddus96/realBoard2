package com.example.realboard;

import com.example.realboard.entity.Member;
import com.example.realboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember(){

        IntStream.rangeClosed(1,30).forEach(i-> {

            Member member = Member.builder()
                    .email("user"+i+"@naver.com")
                    .nickname("user"+i)
                    .password("1111")
                    .build();

            memberRepository.save(member);

        });

    }

}
