package com.example.realboard.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private Long ref;

    @Column(columnDefinition = "integer default 0")
    private int step;

    @Column(columnDefinition = "integer default 0")
    private int refOrder;

    @Column(columnDefinition = "integer default 0")
    private int parentNum;


/*    public void changeRef(Long ref){
        this.ref=ref;
    }*/



}
