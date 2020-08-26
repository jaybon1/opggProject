package com.jaybon.opgg.model.dao;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"user","replies"})
public class Post {

    private long id;
    private String title;
    private String content;

    private int likeCount;
    private int viewCount;

    private User user;

    private List<Reply> replies;

    private Timestamp createDate;

}
