package com.example.realboard;

import com.example.realboard.entity.Member;
import com.example.realboard.repository.BoardImageRepository;
import com.example.realboard.repository.BoardRepository;
import com.example.realboard.repository.MemberRepository;
import com.example.realboard.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardImageRepository imageRepository;

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

    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){

        Long mid = 1L;

        Member member = Member.builder().mid(mid).build();

        replyRepository.deleteByMember(member);
        boardRepository.deleteByMember(member);
        memberRepository.deleteById(mid);


    }

}
