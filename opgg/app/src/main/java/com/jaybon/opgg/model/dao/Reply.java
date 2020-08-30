package com.jaybon.opgg.model.dao;

import java.sql.Timestamp;

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
@ToString
public class Reply {

    private int id;
    private String reply;

    private User user;

    private Post post;

    private Timestamp createDate;

}
