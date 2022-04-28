package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    //ref 테스트
    @Transactional
    @Modifying
    @Query("update Board b set b.ref = b.bno where b.bno = :bno")
    void updateRef(@Param("bno") Long bno);

    //답글 작성시 ref값 동일, parentNum 동일하게 맞추기
    @Transactional
    @Modifying
    @Query("update Board b set b.ref = b.bno")
    void updateRefParentNum(@Param("bno")Long bno);

    @Query("select b from Board b")
    Page<Board> getPagingTests(Pageable pageable);


    // 목록화면에 필요한 데이터 Page<>로 받기
    // Board, Member, count(Reply)
    @Query(value = "select b,m,count (r) " +
            "from Board b " +
            "left join b.member m " +
            "left join Reply r on r.board = b " +
            "GROUP BY b",
             countQuery = "select count (b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    //조회화면에 필요한 Board, Image, Member
    @Query("select b,bi,m " +
            "from Board b " +
            "left join b.member m " +
            "left join BoardImage bi on bi.board = b " +
            "where b.bno = :bno")
    List<Object[]> getBoardWithAll(Long bno);


}
