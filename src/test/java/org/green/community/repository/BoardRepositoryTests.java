package org.green.community.repository;

import jakarta.transaction.Transactional;
import org.green.community.entity.Board;
import org.green.community.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user"+i+"@green.com").build();
            Board board = Board.builder()
                    .title("title...."+i)
                    .content("content..."+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }
    @Transactional
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }
    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr =  (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void testReadWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for(Object[] arr: result){
            System.out.println(Arrays.toString(arr));
        }
    }
    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest
                .of(0,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row->{
           Object[] arr =  (Object[]) row;
           System.out.println(Arrays.toString(arr));
        });
    }
    @Test
    public void testread3(){
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void testSerach1(){
        boardRepository.search1();
    }
    @Test
    public  void testSearch2(){
        boardRepository.search2();
    }
    @Test
    public void testSearch3(){
        boardRepository.search3();
    }
    @Test
    public void testSearchPage(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.searchPage("t", "2", pageable);
        List<Object[]> arr = result.getContent();
        for (Object[] a : arr) {
            System.out.println(a[0].toString());
        }
        System.out.println("****************************************************** 결과 : "+result);
    }
}
