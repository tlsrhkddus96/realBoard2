package com.example.realboard.Service;

import com.example.realboard.dto.BoardLikeDTO;
import com.example.realboard.entity.BoardLike;
import com.example.realboard.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService{

    private final BoardLikeRepository likeRepository;


    @Override
    public int likeCheck(Long bno, Long mid) {

        int result = likeRepository.likeCheck(bno,mid);

        return result;
    }

    @Override
    public void insertBoardLike(BoardLikeDTO boardLikeDTO) {

        BoardLike boardLike = dtoToEntity(boardLikeDTO);

        log.info("BoardLIKE : " + boardLike);

        likeRepository.save(boardLike);


    }

    @Transactional
    @Override
    public void updateLike(Long bno) {

        log.info("UpdateLike " + bno);

        likeRepository.updateLike(bno);

    }

    @Transactional
    @Override
    public void updateLikeCancel(Long bno) {

        log.info("UpdateLikeCancel " + bno);

        likeRepository.updateLikeCancel(bno);

    }

    @Transactional
    @Override
    public void updateLikeCheck(Long bno, Long mid) {

        log.info("UpdateLike Check / bno : " + bno + " / mid : "+ mid);

        likeRepository.updateLikeCheck(bno,mid);

    }



    @Transactional
    @Override
    public void deleteBoardLike(Long bno, Long mid) {

        log.info("delete BoardLike Table");

        likeRepository.deleteBoardLike(bno,mid);

    }


}
