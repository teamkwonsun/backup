package org.green.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type; //검색에 사용함.
    private String keyword;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }
    public Pageable getPageable(Sort sort){

        return PageRequest.of(page-1,size,sort);
    }

}
