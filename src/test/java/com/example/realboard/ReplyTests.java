package com.example.realboard;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.Reply;
import com.example.realboard.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert(){

        IntStream.rangeClosed(1,60).forEach(i-> {

        Long mid = (long)(Math.random()*30)+1;
        Member member = Member.builder().mid(mid).build();

        Long bno = (long)(Math.random()*30)+1;
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .text("Reply.."+i)
                .member(member)
                .board(board)
                .build();

        replyRepository.save(reply);

        });

    }

}
