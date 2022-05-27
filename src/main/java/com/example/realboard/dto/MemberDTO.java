package com.example.realboard.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mid;

    private String email;

    private String nickname;

    private String password;

    private int likePoint;



}
