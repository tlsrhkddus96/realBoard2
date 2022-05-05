package com.example.realboard.Service;

import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.PageRequestDTO;
import com.example.realboard.dto.PageResultDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.entity.Member;
import com.example.realboard.repository.BoardImageRepository;
import com.example.realboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    private final BoardImageRepository imageRepository;

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

        if(result.get(0)[1]!=null) { //해당 게시물에 이미지가 있을 경우

            Board board = (Board) result.get(0)[0];

            List<BoardImage> boardImageList = new ArrayList<>();
            result.forEach(arr -> {
                BoardImage boardImage = (BoardImage) arr[1];
                boardImageList.add(boardImage);
            });

            Member member = (Member) result.get(0)[2];
            Long replyCnt = (Long) result.get(0)[3];

            log.info("board " + board);
            log.info("boardImage" + boardImageList);
            log.info("member " + member);
            log.info("replyCnt " + replyCnt);
            log.info("result[1]" + result.get(0)[1]);


            return entitiesToDTO(board, boardImageList, member, replyCnt);
        }else { //해당 게시물에 이미지가 없을 경우

            Board board = (Board) result.get(0)[0];
            Member member = (Member) result.get(0)[2];
            Long replyCnt = (Long) result.get(0)[3];

            log.info("board " + board);
            log.info("member " + member);
            log.info("replyCnt " + replyCnt);
            log.info("result[1]" + result.get(0)[1]);

            return entitiesToDTONo(board,member,replyCnt);
        }

    }
}
