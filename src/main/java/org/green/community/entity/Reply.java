package org.green.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="myReplySeq", sequenceName = "reply_seq", allocationSize = 1)
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "myReplySeq")
    private Long rno;
    private String text;
    private String replyer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
