package com.example.realboard.controller;

import com.example.realboard.Service.BoardLikeService;
import com.example.realboard.dto.BoardLikeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/like")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;


    @PostMapping("/updateLike")
    public ResponseEntity<Integer> updateLike(@RequestBody BoardLikeDTO boardLikeDTO) throws Exception{

        log.info(boardLikeDTO);

        Long bno = boardLikeDTO.getBno();
        Long mid = boardLikeDTO.getMid();

        int likeCheck = boardLikeService.likeCheck(bno,mid);

        log.info("BoardLikeController like check : " +likeCheck);

        if(likeCheck == 0){
            boardLikeService.insertBoardLike(boardLikeDTO); //BoardLike 테이블 insert
            boardLikeService.updateLike(bno);   //Board 테이블 likeHit +1
            boardLikeService.updateLikeCheck(bno,mid);  //BoardLike 테이블 likeCheck =1
        }else if(likeCheck == 1){
            boardLikeService.updateLikeCancel(bno); // Board 테이블 likeHit -1
            boardLikeService.deleteBoardLike(bno,mid);  // BoardLike 테이블 delete
        }

        return new ResponseEntity<>(likeCheck, HttpStatus.OK);

    }



}
