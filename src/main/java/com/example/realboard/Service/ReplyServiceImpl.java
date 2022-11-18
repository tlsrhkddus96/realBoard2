package com.example.realboard.Service;

import com.example.realboard.dto.ReplyDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.Reply;
import com.example.realboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;


    @Override
    public List<ReplyDTO> getListOfBoard(Long bno) {

        Board board = Board.builder().bno(bno).build();

        List<Reply> result = replyRepository.findByBoard(board);

        return result.stream().map(boardReply ->
                entityToDto(boardReply)).collect(Collectors.toList());
    }


    @Override
    public Long register(ReplyDTO boardReplyDTO) {

        Reply boardReply = dtoToEntity(boardReplyDTO);

        replyRepository.save(boardReply);

        return boardReply.getReplyNum();

    }

    @Override
    public void modify(ReplyDTO boardReplyDTO) {

        Optional<Reply> result = replyRepository.findById(boardReplyDTO.getReplyNum());

        if(result.isPresent()){

            Reply boardReply = result.get();
            boardReply.changeText(boardReplyDTO.getText());

            replyRepository.save(boardReply);

        }

    }

    @Override
    public void remove(Long replyNum) {

        replyRepository.deleteById(replyNum);

    }

}
