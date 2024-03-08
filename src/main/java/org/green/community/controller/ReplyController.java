package org.green.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.green.community.dto.ReplyDTO;
import org.green.community.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", //ex.http://localhost:8081/replies/board/123을 주소창에 입력하면 bno에  123을 받을수 있다. uri템플릿이라고 한다.
                                        //하지만 바로 bno를 쓰지 못한다.메서드 내에서 사용하려면 @PathVariable 과 같이 써야한다.
            produces = MediaType.APPLICATION_JSON_VALUE) //응답 미디어타입을 지정. 클라이언트에게 응답데이터가 무었인지 알려줌.

    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        log.info("bno"+bno);
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK); //첫번쨰자리에는 리턴타입을, 두번쨰 자리에는 클라이언트에게 보내줄 응답상태를 보내준다.
    }
}
