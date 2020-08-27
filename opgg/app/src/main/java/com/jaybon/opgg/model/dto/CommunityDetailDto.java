package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.dao.Post;
import com.jaybon.opgg.model.dao.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDetailDto {

    private int type;

    private Post post;
    private Reply reply;

}
