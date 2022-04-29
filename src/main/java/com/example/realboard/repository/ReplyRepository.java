package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //특정 게시글의 모든 리뷰, 회원의 닉네임
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByBoard(Board board);

    //reply 삭제
    void deleteByMember(Member member);

}
