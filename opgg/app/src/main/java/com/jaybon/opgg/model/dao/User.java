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
public class User {

    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    private Timestamp createDate;

}
