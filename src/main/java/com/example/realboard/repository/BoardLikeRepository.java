package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardLike;
import com.example.realboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardLikeRepository extends JpaRepository<BoardLike,Long> {


    //게시글 추천
    @Modifying
    @Query("update Board set likeHit = likeHit+1 where bno =:bno")
    void updateLike(Long bno);

    //게시글 추천 취소
    @Modifying
    @Query("update Board set likeHit = likeHit-1 where bno =:bno")
    void updateLikeCancel(Long bno);

    //이건 jpa save로
/*    //게시글 추천 시 BoardLike 테이블에 insert
    @Query(value = "insert into BoardLike(likeNum)",
            nativeQuery = true)
    public void insertBoardLike(Long bno, Long mid);*/

    //게시글 추천 취소 시 BoardLike테이블 delete
    @Modifying
    @Query("delete from BoardLike l where l.board.bno=:bno and l.member.mid=:mid ")
    void deleteBoardLike(Long bno, Long mid);

    //게시글 추천 시 likeCheck를 1로 변경 > 중복 방지
    @Modifying
    @Query("update BoardLike l set l.likeCheck = 1 where l.board.bno=:bno and l.member.mid=:mid")
    void updateLikeCheck(Long bno, Long mid);

    //게시글 추천버튼 다시 누를 시 likeCheck 0으로 변경
    @Modifying
    @Query("update BoardLike l set l.likeCheck = 0 where l.board.bno=:bno and l.member.mid=:mid")
    void updateLikeCheckCancel(Long bno, Long mid);

    //likeCheck를 select해서 중복방지
    @Query("select l from BoardLike l where l.board.bno=:bno and l.member.mid=:mid ")
    Integer boardLikeCheck(Long bno, Long mid);






}
