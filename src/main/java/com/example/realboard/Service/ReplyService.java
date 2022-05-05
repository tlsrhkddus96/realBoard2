package com.example.realboard.Service;

import com.example.realboard.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getListOfBoard(Long bno);

    Long register(ReplyDTO boardReplyDTO);

    void modify(ReplyDTO boardReplyDTO);

    void remove(Long replyNum);

}
