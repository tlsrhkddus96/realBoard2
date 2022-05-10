package com.example.realboard.controller;

import com.example.realboard.Service.ReplyService;
import com.example.realboard.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{bno}/all")
    public ResponseEntity<List<ReplyDTO>> getList(@PathVariable("bno") Long bno){

        log.info("---Reply list---");
        log.info(" bno : " + bno);

        List<ReplyDTO> replyDTOList = replyService.getListOfBoard(bno);

        return new ResponseEntity<>(replyDTOList, HttpStatus.OK);

    }

    @PostMapping("/{bno}")
    public ResponseEntity<Long> addReply(@RequestBody ReplyDTO boardReplyDTO){

        log.info("---add Reply----");
        log.info("ReplyDTO : " + boardReplyDTO);

        Long replyNum = replyService.register(boardReplyDTO);

        return new ResponseEntity<>(replyNum,HttpStatus.OK);

    }

    @PutMapping("/{bno}/{replyNum}")
    public ResponseEntity<Long> modifyReply(@PathVariable("replyNum")Long replyNum,
                                            @RequestBody ReplyDTO boardReplyDTO){

        log.info("----Modify Reply ----");
        log.info("replyNum : " + replyNum);
        log.info("replyDTO" + boardReplyDTO);

        replyService.modify(boardReplyDTO);

        return new ResponseEntity<>(replyNum, HttpStatus.OK);

    }

    @DeleteMapping("/{bno}/{replyNum}")
    public ResponseEntity<Long> removeReply(@PathVariable("replyNum")Long replyNum ){

        log.info("----delete Reply-----");
        log.info("replyNum : " + replyNum);

        replyService.remove(replyNum);

        return new ResponseEntity<>(replyNum, HttpStatus.OK);

    }


}
