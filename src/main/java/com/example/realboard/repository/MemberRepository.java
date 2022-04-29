package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {


    //어떤 member를 사용하는 board전부 불러오기
/*
    @Query("select Board from Member")
    Board findBoardByMember(Member member);
*/


}
