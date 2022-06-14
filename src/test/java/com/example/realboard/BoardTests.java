package com.example.realboard;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.entity.Member;
import com.example.realboard.repository.BoardImageRepository;
import com.example.realboard.repository.BoardRepository;
import com.example.realboard.repository.MemberRepository;
import com.example.realboard.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
public class BoardTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardImageRepository imageRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertTests(){

        LongStream.rangeClosed(1,30).forEach(i -> {

            Member member = Member.builder().mid(i).build();

            Board board = Board.builder()
                    .content("임시"+i)
                    .title("임시제목"+i)
                    .ref((int) i)
                    .member(member)
                    .build();
            boardRepository.save(board);
            boardRepository.updateRef(board.getBno());

            int count = (int) (Math.random() * 2) +1;

            for(int j = 0; j<count; j++){

                BoardImage boardImage = BoardImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .board(board)
                        .imageName("test"+j+".jpg").build();

                imageRepository.save(boardImage);

            }

        });

    }

    @Test
    public void insertKid(){

        //Read페이지에서 답글 작성 버튼 누르면 bno값을 전달한다.
        //전달한 bno를 가지고 findByID로 객체 데이터를 가져옴
        Optional<Board> parentBoard = boardRepository.findById(13L);

        Member member = Member.builder().mid(10L).build();



        //필요한건 해당 bno의 ref값과 bno를 parentNum으로 지정. step,refOrder를 가져와 +1 시킴
        Board board = Board.builder()
                .title(" Re : " + parentBoard.get().getTitle())
                .content("dd")
                .parentNum(parentBoard.get().getBno().intValue())
                .ref(parentBoard.get().getRef())
                .step(parentBoard.get().getStep()+1)
                .refOrder(parentBoard.get().getRefOrder()+1)
                .member(member)
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

    @Test
    public void testGetListPage(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("ref").descending().and(Sort.by("refOrder").ascending()));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });

    }

    @Test
    public void testGetBoard(){

        List<Object[]> result = boardRepository.getBoardWithAll(55L);

        System.out.println(result);

        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }

    }


    @Transactional
    @Commit
    @Test
    public void testDeleteBoardByMember(){


        Long mid = 4L;

        Member member = Member.builder().mid(mid).build();

        List<Board> result = boardRepository.findBoardByMember(member);  // 해당 mid의 board들을 list로 받음

        result.forEach(board ->{
            //이제 Board객체를 구했으니깐 해당 객체에 있는 BoardImage를 지워줘야함
            imageRepository.deleteByBoard(board);
            //리플도 지워야함
            replyRepository.deleteByBoard(board);
        });

        //이제 Board 객체를 지우면 됨
        boardRepository.deleteByMember(member);





    }

    @Test
    public void testRefParentNum(){

        Long bno =58L;
        Long parentNum=12L;

        boardRepository.updateRefParentNum(bno,parentNum);

    }


    @Transactional
    @Test
    public void testSearch1(){

        Pageable pageable =
                PageRequest.of(0,10,Sort.by("ref").descending()
                        .and(Sort.by("refOrder").ascending()).
                        and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t","1",pageable);


    }




}
