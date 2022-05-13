package com.example.realboard.Service;

import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.PageRequestDTO;
import com.example.realboard.dto.PageResultDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.entity.Member;
import com.example.realboard.repository.BoardImageRepository;
import com.example.realboard.repository.BoardRepository;
import com.example.realboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    private final BoardImageRepository imageRepository;

    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public Long register(BoardDTO boardDTO) {

        log.info(boardDTO);


        Map<String,Object> entityMap = dtoToEntity(boardDTO);

        Board board = (Board) entityMap.get("board");
        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");

        boardRepository.save(board);
        boardRepository.updateRef(board.getBno()); // Ref bno와 맞추기

        if ((List<BoardImage>)entityMap.get("imgList") !=null){ //이미지가 있을경우만
            boardImageList.forEach(boardImage -> {
                imageRepository.save(boardImage);
            });
        }

/*        boardImageList.forEach(boardImage -> {
            imageRepository.save(boardImage);
        });*/

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {

        log.info("PageRequestDTO : " + requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("ref").descending().and(Sort.by("refOrder").ascending()));

       // Page<Object[]> result = boardRepository.getListPage(pageable);
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        Function<Object[], BoardDTO> fn = (en -> entitiesToDTONo(
                (Board) en[0],
                (Member) en[1],
                (Long) en[2]
        ));

       /* Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (Board) arr[0],
                (List<BoardImage>)(Arrays.asList((BoardImage)arr[1])),
                (Member) arr[2],
                (Long) arr[3]
        ));*/

        return new PageResultDTO<>(result,fn);

    }

    @Override
    public BoardDTO getBoard(Long bno) {

        List<Object[]> result = boardRepository.getBoardWithAll(bno);

        // getBoardWithAll >> Board, BoardImage, Member, (count)Reply 오브젝트 배열로 모아둠
        Board board = (Board) result.get(0)[0];
        Member member = (Member) result.get(0)[2];
        Long replyCnt = (Long) result.get(0)[3];

        log.info("board " + board);
        log.info("member " + member);
        log.info("replyCnt " + replyCnt);
        log.info("result[1]" + result.get(0)[1]);

        if(result.get(0)[1]!=null) { //해당 게시물에 이미지가 있을 경우
            
            List<BoardImage> boardImageList = new ArrayList<>();
            result.forEach(arr -> {
                BoardImage boardImage = (BoardImage) arr[1];
                boardImageList.add(boardImage);
            });

            log.info("boardImage" + boardImageList);

            return entitiesToDTO(board, boardImageList, member, replyCnt);

        }else { //해당 게시물에 이미지가 없을 경우

            return entitiesToDTONo(board,member,replyCnt);

        }

    }

    @Transactional
    @Override
    public void removeBoard(Long bno) {

        Board board = Board.builder().bno(bno).build();

        replyRepository.deleteByBoard(board);   //해당 board의 reply삭제
        imageRepository.deleteByBoard(board);   //해당 board의 image삭제
        boardRepository.deleteById(bno);        //해당 bno 테이블 삭제

    }

    @Transactional
    @Override
    public void modifyBoard(BoardDTO boardDTO) {

      //Board board = Board.builder().bno(boardDTO.getBno()).build();

        Long bno = boardDTO.getBno();

        Board board = boardRepository.findBoardByBno(bno);

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);

    }

    @Transactional
    @Override
    public void modifyBoardTest(BoardDTO boardDTO) {

        log.info(boardDTO);

        Map<String,Object> entityMap = dtoToEntity(boardDTO);

        Board board = (Board) entityMap.get("board");

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
        boardRepository.updateRef(board.getBno());

        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");

        if ((List<BoardImage>)entityMap.get("imgList") !=null){ //이미지가 있을경우만
            boardImageList.forEach(boardImage -> {
                imageRepository.save(boardImage);
            });
        }

    }

    @Override
    public Long kidRegister(BoardDTO boardDTO) {

        log.info(boardDTO);

        Integer maxRefOrder = boardRepository.findMaxRefOrder(boardDTO.getRef());
        log.info("max RefOrder : " + maxRefOrder);

        Board parentBoard = boardRepository.findBoardByBno((long)boardDTO.getParentNum());
        log.info("parentBoard : " + parentBoard);

        Map<String,Object> entityMap = kidDtoToEntity(boardDTO,parentBoard,maxRefOrder);

        Board board = (Board) entityMap.get("board");
        List<BoardImage> boardImageList = (List<BoardImage>) entityMap.get("imgList");


        boardRepository.save(board);

        if ((List<BoardImage>)entityMap.get("imgList") !=null){ //이미지가 있을경우만
            boardImageList.forEach(boardImage -> {
                imageRepository.save(boardImage);
            });
        }

/*        boardImageList.forEach(boardImage -> {
            imageRepository.save(boardImage);
        });*/

        return board.getBno();
    }
}