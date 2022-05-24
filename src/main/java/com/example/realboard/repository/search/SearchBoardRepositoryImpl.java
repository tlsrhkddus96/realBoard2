package com.example.realboard.repository.search;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.QBoard;
import com.example.realboard.entity.QMember;
import com.example.realboard.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1()..........");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.member.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.mid, reply.count());

        tuple.groupBy(board);

        log.info("==========================");
        log.info(tuple);
        log.info("==========================");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;

    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("SearchPage ,,,");

        return null;
    }
}
