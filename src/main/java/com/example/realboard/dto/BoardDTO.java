package com.example.realboard.dto;

import com.example.realboard.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private Long mid; // 멤버의 id값

    private LocalDateTime regDate, modDate;

    //페이징에 필요한 Member의 nickname, count(Reply) int
    private int replyCnt;
    private String nickname;

    private Long parentNum;
    private int ref;
    private int refOrder;

    private int step;

    @Builder.Default
    private List<BoardImageDTO> imageDTOList = new ArrayList<>();



}
