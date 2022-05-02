package com.example.realboard;

import com.example.realboard.entity.Board;
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
import java.util.List;
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

        //멤버 하나 삭제하려면
        //해당 mid로 작성된 board 삭제                  만약 mid 1개가 2개의 board를 작성했다면? 2개의 board에 있는 image도 삭제
        // ㄴ 해당 board의 bi가 있을경우 이것도 삭제
        //해당 mid로 작성된 reply 삭제

        Long mid = 5L;
        Member member = Member.builder().mid(mid).build();
        replyRepository.deleteByMember(member); //이건 걍 지우면됨 (board도 묶여있으니 board도)

        //이제 해당 member객체를 사용하는 board를 지워야함
        //그 전에 그 board들이 사용중인 boardimage를 삭제

        //먼저 해당member가 작성한 board들을 List로 받고
        List<Board> result = boardRepository.findBoardByMember(member);
        result.forEach(board -> {
            //그 board들의 이미지를 삭제해줌
            imageRepository.deleteByBoard(board);
            //reply도 묶여있으니 reply도 삭제
            replyRepository.deleteByBoard(board);
        });

        //member가 작성한 board들의 image,reply가 삭제됐으니 board객체도 삭제
        boardRepository.deleteByMember(member);

        //해당member는 이제 fk로 잡혀져있는게 없으니 바로삭제
        memberRepository.deleteById(mid);


    }

/*    @Test
    public void testGet(){

        Long mid = 2L;

        Member member = Member.builder().mid(mid).build();

        memberRepository.findBoardByMember(member);

    }*/

}
