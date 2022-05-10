package com.example.realboard.Service;

import com.example.realboard.dto.ReplyDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getListOfBoard(Long bno);

    Long register(ReplyDTO boardReplyDTO);

    void modify(ReplyDTO boardReplyDTO);

    void remove(Long replyNum);


    default Reply dtoToEntity(ReplyDTO boardReplyDTO){

        Reply boardReply = Reply.builder()
                .replyNum(boardReplyDTO.getReplyNum())
                .board(Board.builder().bno(boardReplyDTO.getBno()).build())
                .member(Member.builder().mid(boardReplyDTO.getMid()).build())
                .text(boardReplyDTO.getText())
                .build();

        return boardReply;

    }

    default ReplyDTO entityToDto(Reply boardReply){

        ReplyDTO boardReplyDTO = ReplyDTO.builder()
                .replyNum(boardReply.getReplyNum())
                .bno(boardReply.getBoard().getBno())
                .mid(boardReply.getMember().getMid())
                .nickname(boardReply.getMember().getNickname())
                .text(boardReply.getText())
                .regDate(boardReply.getRegDate())
                .modDate(boardReply.getModDate())
                .build();

        return boardReplyDTO;

    }


}
