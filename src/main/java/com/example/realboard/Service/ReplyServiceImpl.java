package com.example.realboard.Service;

import com.example.realboard.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    @Override
    public List<ReplyDTO> getListOfBoard(Long bno) {
        return null;
    }

    @Override
    public Long register(ReplyDTO boardReplyDTO) {
        return null;
    }

    @Override
    public void modify(ReplyDTO boardReplyDTO) {

    }

    @Override
    public void remove(Long replyNum) {

    }

}
