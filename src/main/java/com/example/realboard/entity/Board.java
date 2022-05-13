package com.example.realboard.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = "member")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private int ref;

    @Column(columnDefinition = "integer default 0")
    private int step;

    @Column(columnDefinition = "integer default 0")
    private int refOrder;

    @Column(columnDefinition = "integer default 0")
    private int parentNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }





}
