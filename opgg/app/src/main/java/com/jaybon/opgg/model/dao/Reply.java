package com.jaybon.opgg.model.dao;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    private long id;
    private long postId;
    private long userId;
    private String reply;
    private Timestamp createDate;
}
