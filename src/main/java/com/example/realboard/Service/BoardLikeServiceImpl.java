package com.example.realboard.Service;

import com.example.realboard.dto.BoardLikeDTO;
import com.example.realboard.entity.BoardLike;
import com.example.realboard.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService{

    private final BoardLikeRepository likeRepository;


    @Override
    public int likeCheck(Long bno, Long mid) {

        int result = likeRepository.boardLikeCheck(bno,mid);

        return result;
    }

    @Override
    public void insertBoardLike(BoardLikeDTO boardLikeDTO) {

        BoardLike boardLike = dtoToEntity(boardLikeDTO);

        log.info("BoardLIKE : " + boardLike);

        likeRepository.save(boardLike);


    }




}
