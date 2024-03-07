package org.green.community.service;

import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService service;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("테스트")
                .content("내용내용")
                .writerEmail("user1@green.com")
                .build();
        Long bno = service.register(dto);
        System.out.println("등록된 번호 : "+bno);
    }

    @Test
    public void testlist(){
        PageRequestDTO dto = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = service.getList(dto);
        for(BoardDTO bdto: result.getDtoList()){
            System.out.println(bdto);
        }
    }
    @Test
    public void testGet(){
        BoardDTO dto = service.get(100L);
        System.out.println(dto);
    }
    @Test
    public void testRemove(){
        service.removeWithReply(100L);
    }
    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("랄랄라")
                .content("내용내용")
                .build();
        service.modify(boardDTO);
    }
}
