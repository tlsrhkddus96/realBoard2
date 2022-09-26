package com.example.realboard;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.Reply;
import com.example.realboard.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyTests {

    @Autowired
    private ReplyRepository replyRepository;




    @Test
    public void testGetBoardReply(){

        Board board = Board.builder().bno(20L).build();

        List<Reply> result = replyRepository.findByBoard(board);

        result.forEach(boardReply -> {

            System.out.println(boardReply.getReplyNum());
            System.out.println(boardReply.getText());
            System.out.println(boardReply.getMember().getEmail());

        });

    }

    @Test
    public void testReplyGGet(){

        Board board = Board.builder().bno(189L).build();

        List<Reply> result = replyRepository.findByBoard(board);


    }


    @Transactional
    @Commit
    @Test
    public void DeleteReplyByMember(){

        Long mid = 1L;

        Member member = Member.builder().mid(mid).build();

        replyRepository.deleteByMember(member);

    }

}
