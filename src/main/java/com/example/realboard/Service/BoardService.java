package com.example.realboard.Service;

import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.BoardImageDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO boardDTO);


    //Board객체와 BoardImage객체를 같이 처리하기 위해 Map타입 사용
    default Map<String, Object> dtoToEntity(BoardDTO boardDTO){

        Map<String,Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(boardDTO.getMid()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .member(member)
                .build();

        entityMap.put("board",board);

        List<BoardImageDTO> imageDTOList = boardDTO.getImageDTOList();

        //BoardImageDTO처리
        if(imageDTOList != null && imageDTOList.size()>0){

            List<BoardImage> boardImageList = imageDTOList.stream().map(
                    boardImageDTO -> {

                        BoardImage boardImage = BoardImage.builder()
                                .path(boardImageDTO.getPath())
                                .imageName(boardImageDTO.getImgName())
                                .uuid(boardImageDTO.getUuid())
                                .board(board)
                                .build();
                        return boardImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList", boardImageList);
        }


        return entityMap;

    }


}
