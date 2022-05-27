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
    public ResponseEntity<Long> updateLike(@RequestBody BoardLikeDTO boardLikeDTO) throws Exception{

        log.info(boardLikeDTO);

        Long bno = boardLikeDTO.getBno();
        Long mid = boardLikeDTO.getMid();

        // likeCheck를 하기전에 테이블에 데이터를 심어야되는데 흠...
        // 데이터를 먼저 심으면  버튼을 또 누르면 동일값의 dto를 보내서 에러가 남
        // 초기에는 likeCheck를 0? service단에서 likeCheck가 0이면 변경하는?
        // 아니면 likeCheck말고 컬럼 하나 더 만들어서 걍 ++ 되게 하고 1.insertBoardLike 먼저하고 아 시발 그럼 어케해



        int likeCheck = boardLikeService.likeCheck(bno,mid);

        log.info(likeCheck);

        if(likeCheck == 0){
            // BoardLike 테이블 insert (BoardLikeDTO)
            boardLikeService.insertBoardLike(boardLikeDTO);
            // Board테이블 likeHit +1 (bno)
            // BoardLike 테이블 likeCheck =1 (bno,mid)
            // 회원포인트? 나중에 구현
        }else if(likeCheck == 1){

        }

        return new ResponseEntity<>(boardLikeDTO.getLikeNum(), HttpStatus.OK);

    }



}
