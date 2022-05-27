package com.example.realboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardLikeDTO {

    private Long likeNum;

    private Long bno;

    private Long mid;

    private int likeCheck;

}
