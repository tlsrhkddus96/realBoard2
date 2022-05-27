package com.example.realboard.Service;

import com.example.realboard.dto.BoardLikeDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardLike;
import com.example.realboard.entity.Member;

public interface BoardLikeService {

    int likeCheck(Long bno, Long mid);

    void insertBoardLike(BoardLikeDTO boardLikeDTO);


    default BoardLikeDTO entityToDTO(BoardLike boardLike){

        BoardLikeDTO boardLikeDTO = BoardLikeDTO.builder()
                .likeNum(boardLike.getLikeNum())
                .bno(boardLike.getLikeNum())
                .mid(boardLike.getLikeNum())
                .likeCheck(boardLike.getLikeCheck())
                .build();

        return boardLikeDTO;
    }

    default BoardLike dtoToEntity(BoardLikeDTO boardLikeDTO){

        BoardLike boardLike = BoardLike.builder()
                .likeNum(boardLikeDTO.getLikeNum())
                .board(Board.builder().bno(boardLikeDTO.getBno()).build())
                .member(Member.builder().mid(boardLikeDTO.getMid()).build())
                .likeCheck(boardLikeDTO.getLikeCheck())
                .build();

        return boardLike;

    }

}
