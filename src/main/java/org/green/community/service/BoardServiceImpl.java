package org.green.community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Member;
import org.green.community.repository.BoardRepository;
import org.green.community.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        repository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn =
                (en-> entityToDTO((Board)en[0],(Member) en[1] ,(Long) en[2]));
//        Page<Object[]> result =
//                repository.getBoardWithReplyCount(pageRequestDTO
//                        .getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = repository.searchPage(pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]) ;
    }
    @Transactional
    @Override
    public void removeWithReply(Long bno) {
        //bno참조하는 댓글 먼저삭제
        replyRepository.deleteByBno(bno);
        //게시글삭제
        repository.deleteById(bno);
    }
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
       Board board = repository.getReferenceById(boardDTO.getBno());
       if(board != null){
           board.changeTitle(boardDTO.getTitle());
           board.changeContent(boardDTO.getContent());
           repository.save(board);
       }
    }
}
