package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,300).forEach(i->{
            //1~100임의의 번호
            long bno = (long) (Math.random()*100)+1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply..."+i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);
        });
    }
    @Test
    public void testListByBoard(){
        List<Reply> replylist = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(85L).build());
        replylist.forEach(reply-> System.out.println(reply));
    }

}
