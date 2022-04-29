package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {



    //해당board에 있는 image 삭제
    @Modifying
    @Query("delete from BoardImage bi where bi.board = :board")
    void deleteByBoard(Board board);





}
