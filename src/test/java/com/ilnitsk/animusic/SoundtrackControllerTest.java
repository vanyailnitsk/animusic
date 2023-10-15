package com.ilnitsk.animusic;

import com.ilnitsk.animusic.controllers.SoundtrackController;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import com.ilnitsk.animusic.services.SoundtrackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SoundtrackControllerTest {

    @Mock
    private SoundtrackRepository soundtrackRepository;

    @InjectMocks
    private SoundtrackController soundtrackController;
    @Mock
    private SoundtrackService soundtrackService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(soundtrackController).build();
    }

    @Test
    public void testGetAudioStream() throws Exception {
        Soundtrack soundtrack = new Soundtrack();
        soundtrack.setId(1);
        soundtrack.setPathToFile("path/to/file.mp3");

        when(soundtrackRepository.findById(1)).thenReturn(Optional.of(soundtrack));
        when(soundtrackService.getAudioStream(anyInt(), any())).thenReturn(ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_LENGTH, "2000000")
                .header(HttpHeaders.CONTENT_RANGE, "bytes 0-1999999/1234567")
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(out -> {
                    // your stream logic here
                }));

        // Выполняем запрос к контроллеру
        mockMvc.perform(get("/api/soundtracks/play/{id}", 1))
                .andExpect(status().isPartialContent())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,"audio/mpeg"));
    }

    // Другие тесты могут быть добавлены здесь
}

