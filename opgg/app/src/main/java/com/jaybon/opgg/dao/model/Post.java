package com.jaybon.opgg.dao.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private long id;
    private long userId;
    private String title;
    private String Content;
    private int like;
    private Timestamp createDate;
}
