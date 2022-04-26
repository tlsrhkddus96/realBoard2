package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

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


}
