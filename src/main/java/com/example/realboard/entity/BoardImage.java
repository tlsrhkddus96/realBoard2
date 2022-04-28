package com.example.realboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Builder
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imageName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
