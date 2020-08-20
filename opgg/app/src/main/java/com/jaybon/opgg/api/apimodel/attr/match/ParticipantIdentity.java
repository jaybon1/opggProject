package com.jaybon.opgg.api.apimodel.attr.match;

import com.jaybon.opgg.api.apimodel.ApiMatch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantIdentity {

    private long participantId;
    private Player player;

}