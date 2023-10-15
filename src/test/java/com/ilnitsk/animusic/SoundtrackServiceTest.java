package com.ilnitsk.animusic;

import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.PlaylistRepository;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import com.ilnitsk.animusic.services.Downloader;
import com.ilnitsk.animusic.services.SoundtrackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SoundtrackServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private SoundtrackRepository soundtrackRepository;
    @Mock
    private AnimeRepository animeRepository;

    @Mock
    Downloader downloader = Mockito.mock(Downloader.class);

    @InjectMocks
    private SoundtrackService soundtrackService;


    @Test
    public void testStreamSoundtrack() {

    }
}

