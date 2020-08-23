package com.jaybon.opgg.model.apidao.apimodel;

import com.jaybon.opgg.model.apidao.apimodel.attr.rank.Entry;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRank {

    public String tier;
    public String leagueId;
    public String queue;
    public String name;
    public List<Entry> entries;

}