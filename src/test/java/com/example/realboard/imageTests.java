package com.example.realboard;

import com.example.realboard.entity.Board;
import com.example.realboard.repository.BoardImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
public class imageTests {

    @Autowired
    private BoardImageRepository imageRepository;

    @Transactional
    @Commit
    @Test
    public void testDelete(){

        Long bno = 2L;

        Board board = Board.builder().bno(bno).build();

        imageRepository.deleteByBoard(board);

    }

}
