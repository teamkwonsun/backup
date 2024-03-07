package org.green.community.service;

import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Member;

public interface BoardService {
    //등록
    Long register(BoardDTO dto);

    //게시글 목록조회
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //게시글 상세조회
    BoardDTO get(Long bno);

    //게시글 삭제
    void removeWithReply(Long bno);

    //게시글 수정
    void modify(BoardDTO boardDTO);

    //dto---> entity로변환 메소드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }
    //entity를 dto로 변환 메소드
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return boardDTO;
    }
}
