package org.green.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
@SequenceGenerator(name="myBoardSeq", sequenceName = "board_seq", allocationSize = 1)
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "myBoardSeq")
    private Long bno;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;  //연관관계 설정

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
}
