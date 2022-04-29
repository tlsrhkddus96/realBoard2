package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //특정 게시글의 모든 리뷰, 회원의 닉네임
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByBoard(Board board);

    //member가 사용중인 reply삭제
    @Modifying
    @Query("delete from Reply r where r.member =:member")
    void deleteByMember(Member member);

    //board가 사용중인 reply삭제
    @Modifying
    @Query("delete from Reply r where r.board =:board")
    void deleteByBoard(Board board);

}
