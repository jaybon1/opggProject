package com.jaybon.opgg.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoLoginDto {

    public String kakaoId;
    public String name;
    public String email;

}