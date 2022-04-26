package com.example.realboard;

import com.example.realboard.entity.Board;
import com.example.realboard.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
public class BoardTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTests(){

        LongStream.rangeClosed(1,2).forEach(i -> {

            Board board = Board.builder()
                    .content("임시"+i)
                    .title("임시제목"+i)
                    .ref(i)
                    .build();
            boardRepository.save(board);
            boardRepository.updateRef(board.getBno());

        });

    }

    @Test
    public void setRef(){


        Long bno = 14L;

        boardRepository.updateRef(bno);


    }

    @Test
    public void insertKid(){

        //Read페이지에서 답글 작성 버튼 누르면 bno값을 전달한다.
        //전달한 bno를 가지고 findByID로 객체 데이터를 가져옴
        Optional<Board> parentBoard = boardRepository.findById(10L);


        //필요한건 해당 bno의 ref값과 bno를 parentNum으로 지정. step,refOrder를 가져와 +1 시킴
        Board board = Board.builder()
                .title("ㄴ Re : " + parentBoard.get().getTitle())
                .content("dd")
                .parentNum(parentBoard.get().getBno().intValue())
                .ref(parentBoard.get().getRef())
                .step(parentBoard.get().getStep()+1)
                .refOrder(parentBoard.get().getRefOrder()+1)
                .build();

        boardRepository.save(board);


    }


    @Test
    public void testPaging(){


        Pageable pageable = PageRequest.of(0,10,Sort.by("ref").descending().and(Sort.by("refOrder").ascending()));

        Page<Board> result = boardRepository.getPagingTests(pageable);

        result.get().forEach(row -> {
            Board arr = row;

            System.out.println(arr);
        });


    }

}
