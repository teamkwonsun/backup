package org.green.community.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> {
    //DTO리스트
    //DTO리스트
    private List<DTO> dtoList;
    //총페이지 번호
    private int totalPage;
    //현재 페이지 번호
    private int page;
    //목록사이즈
    private int size;
    //시작 페이지 번호,끝 페이지 번호
    private int start,end;
    //이전 ,다음
    private boolean prev,next;
    //페이지 번호 목록
    private List<Integer> pageList;



    //생성자
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int) (Math.ceil(page/10.0))*10;  //1~10  --> 10, 11~20 -> 20
        start = tempEnd - 9;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        prev = start > 1;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
