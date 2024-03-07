package org.green.community.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.green.community.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport
implements SearchBoardRepository{
    public SearchBoardRepositoryImpl() {
        super(Board.class); //부모객체의 생성자를 명시적으로 호출해야함. 부모객체의 생성자가 기본생성자가 아닌경우.
    }

    @Override
    public Board search1() {
        log.info("search1............................");

        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board); //from메서드로 jpqlQuery객체를 생성함.
        //Querydsl의 from 메서드를 사용하여 JPQLQuery객체를 생성
        //Q타입 객체 인자로 넣어줌.
        jpqlQuery.select(board).where(board.bno.eq(1L));

        log.info("-----------------------------------------------------------");
        log.info(jpqlQuery);
        log.info("-----------------------------------------------------------");

        List<Board> result = jpqlQuery.fetch();

        return null;
    }

    @Override
    public Board search2() {
        log.info("search2.................................");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.select(board,member).where(board.bno.eq(1L));
        log.info(jpqlQuery);
        List<Board> result = jpqlQuery.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Board search3() {
        log.info("search3........................................");
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board,member);
        List<Tuple> result = tuple.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("serchPage...............................");
        //Q도메인 객체 생성
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //JPQLQuery객체 생성 + 조인
        JPQLQuery<Board> jpqlQuery = from(board).leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //조회
        JPQLQuery<Tuple> tupleJPQLQuery = jpqlQuery.select(board,member.regDate.count());
        //where절 설정
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);

        if(type!=null){
            String[] typeArr = type.split(""); //list에 검색폼에 type이 있음
            //검색조건작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String s : typeArr) {
                switch (s){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tupleJPQLQuery.where(booleanBuilder);
        tupleJPQLQuery.groupBy(board, member);
        //페이징 추가.
        this.getQuerydsl().applyPagination(pageable,tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        log.info(result);
        long count = tupleJPQLQuery.fetchCount();
        log.info(count);

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()),pageable,count   );
    }
}
