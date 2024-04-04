package com.ilnitsk.animusic.image.service;

import com.ilnitsk.animusic.image.repository.CoverArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverArtService {
    private final CoverArtRepository coverArtRepository;
    private final ImageService imageService;
}
