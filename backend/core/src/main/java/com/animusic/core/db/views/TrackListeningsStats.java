package com.animusic.core.db.views;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TrackListeningsStats {

    Integer trackId;

    Integer count;
}
