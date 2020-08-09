package com.jaybon.opgg.dao.dto;

import com.jaybon.opgg.dao.model.Post;
import com.jaybon.opgg.dao.model.Reply;
import com.jaybon.opgg.dao.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityDto {
    private Post post;
    private String nickname;
    private int replyCount;
    private int page;
    private int type;
}
