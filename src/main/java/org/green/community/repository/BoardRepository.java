package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {

    //연관관계가 있는 엔티티조인 처리
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //연관관계가 없는 엔티티조인 처리
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value="select b, w, count(r) from Board b left join b.writer w" +
            " left join Reply r on r.board = b group by b, w",
    countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageble);

    //조회화면에서 필요한 jpql
    @Query("select b, w, count(r) from Board b left join b.writer w " +
            "left join Reply r on r.board = b " +
            "where b.bno=:bno group by b, w")
    Object getBoardByBno(@Param("bno") Long bno);
}
