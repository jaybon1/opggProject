package com.jaybon.opgg.model.apidao.apimodel.attr.match;

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