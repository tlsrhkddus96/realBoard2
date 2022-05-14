package com.example.realboard.repository;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    //ref 테스트 ++ parentNum 추가
    @Transactional
    @Modifying
    @Query("update Board b set b.ref = b.bno where b.bno = :bno")
    void updateRef(@Param("bno") Long bno);

    //답글 작성시 ref값 동일, parentNum 동일하게 맞추기
    @Transactional
    @Modifying
    @Query("update Board b set b.ref=:parentNum , b.parentNum=:parentNum" +
            " where b.bno =:bno")
    void updateRefParentNum(@Param("bno")Long bno,@Param("parentNum")Long parentNum);


    @Query("select b from Board b")
    Page<Board> getPagingTests(Pageable pageable);


    // 목록
    // Board, Member, count(Reply)
    @Query(value = "select b,m,count (r) " +
            "from Board b " +
            "left join b.member m " +
            "left join Reply r on r.board = b " +
            "GROUP BY b",
             countQuery = "select count (b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    // 목록화면에 필요한 데이터 Page<>로 받기
    // Board, BoardImage, Member, count(reply)
    @Query("select b,bi,m,count (r) " +
            "from Board b " +
            "left join BoardImage bi on bi.board = b " +
            "left join b.member m " +
            "left join Reply r on r.board = b " +
            "group by b")
    Page<Object[]> getListPage(Pageable pageable);


/*    //조회화면에 필요한 Board, Image, Member 92p>reply추가시
    @Query("select b,bi,m " +
            "from Board b " +
            "left join b.member m " +
            "left join BoardImage bi on bi.board = b " +
            "where b.bno = :bno")
    List<Object[]> getBoardWithAll(Long bno);*/

    //조회화면에 필요한 Board, Image, Member 92p>reply추가시
    @Query("select b,bi,m, count(r) " +
            "from Board b " +
            "left join BoardImage bi on bi.board = b " +
            "left join b.member m " +
            "left join Reply r on r.board = b " +
            "where b.bno = :bno group by bi" )
    List<Object[]> getBoardWithAll(Long bno);

    //member객체를 사용하는 게시물 삭제
    @Modifying
    @Query("delete from Board b where b.member = :member")
    void deleteByMember(Member member);


    //어떤 member를 사용하는 board전부 불러오기
    @Query("select b from Board b where b.member = :member")
    List<Board> findBoardByMember(Member member);

    //bno값으로 Board객체 가져오기
    @Query("select b from Board b where b.bno = :bno")
    Board findBoardByBno(Long bno);


/*    @Query("select max(b.refOrder) from Board b where b.ref =:ref")
    Integer findMaxRefOrder(int ref);*/


    //답글 작성시 원글의 ref, reforder를 가져와 해당 refOrder보다 높은 값을 +1 해주고 작성한 답글은 원글의 refOrder+1
    @Modifying
    @Query("update Board b set b.refOrder = b.refOrder +1 where b.ref= :ref and b.refOrder > :refOrder")
    void updateRefOrder(int ref, int refOrder);





}
