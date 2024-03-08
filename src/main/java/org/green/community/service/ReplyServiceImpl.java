package org.green.community.service;

import com.fasterxml.jackson.databind.node.ValueNode;
import lombok.RequiredArgsConstructor;
import org.green.community.dto.BoardDTO;
import org.green.community.dto.ReplyDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Reply;
import org.green.community.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    //등록
    @Override
    public Long register(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto); //save에 엔티티타입을 넣어줘야하므로 변환
        replyRepository.save(reply  );
        return reply.getRno();

    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream().map(reply->entityToDTO(reply)).collect(Collectors.toList()); //엔티티가 들어있던 타입을 List<ReplyDTO>으로
    }

    @Override
    public void modify(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply  );
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
