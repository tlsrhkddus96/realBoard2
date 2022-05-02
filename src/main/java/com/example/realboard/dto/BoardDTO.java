package com.example.realboard.dto;

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

   //private String nickname; // 멤버의 닉네임

    private LocalDateTime regDate, modDate;

    @Builder.Default
    private List<BoardImageDTO> imageDTOList = new ArrayList<>();



}
