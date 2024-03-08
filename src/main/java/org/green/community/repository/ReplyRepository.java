package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.entity.Member;
import org.green.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
   //JPQL을 이용해서 update,delete를 실행하기 위해서 지정
    @Modifying
    //게시글 삭제요청시 댓글 삭제
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);

    //게시물로 댓글 가져오기 댓글 조회
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
