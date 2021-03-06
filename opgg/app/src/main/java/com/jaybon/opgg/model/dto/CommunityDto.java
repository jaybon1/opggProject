package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.dao.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommunityDto {

    private int type;

    private Post post;

}
