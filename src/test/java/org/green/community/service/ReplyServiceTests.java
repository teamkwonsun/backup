package org.green.community.service;

import org.green.community.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {
    @Autowired
    private ReplyService service;
    @Test
    public void testGetList(){
        List<ReplyDTO> result = service.getList(50L);
        result.forEach(re-> System.out.println(re));
    }
    @Test
    public void testInsert(){
        Long rno = service.register(ReplyDTO.builder().replyer("user").text("내용").bno(60L).build());
        System.out.println(rno);
    }
}
