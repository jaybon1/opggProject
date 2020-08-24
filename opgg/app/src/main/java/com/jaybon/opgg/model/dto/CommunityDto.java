package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.dao.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityDto {

    private int type;

    private Post post;
    private String nickname;
    private int replyCount;
    private int page;
}
