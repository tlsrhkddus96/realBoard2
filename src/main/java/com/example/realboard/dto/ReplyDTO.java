package com.example.realboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long replyNum;

    //
    private Long bno;
    private Long mid;

    private String nickname;

    private String text;

    private LocalDateTime regDate,modDate;

}
