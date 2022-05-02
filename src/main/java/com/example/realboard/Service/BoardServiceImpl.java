package com.example.realboard.Service;

import com.example.realboard.dto.BoardDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.repository.BoardImageRepository;
import com.example.realboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
        /*
        boardImageList.forEach(boardImage -> {
            imageRepository.save(boardImage);
        });*/

        return board.getBno();
    }
}
