package com.animusic.content.subscription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.SubscriptionForAlbumRepository;
import com.animusic.core.db.table.SubscriptionForAnimeRepository;
import com.animusic.core.db.utils.ContentSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.animusic.core.db.utils.SubscriptionTargetType.ALBUM;
import static com.animusic.core.db.utils.SubscriptionTargetType.ANIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentSubscriptionServiceTest {

    @Mock
    SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    @Mock
    SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    ContentSubscriptionService contentSubscriptionService;

    @BeforeEach
    void setUp() {
        this.contentSubscriptionService = new ContentSubscriptionService(
                subscriptionForAnimeRepository,
                subscriptionForAlbumRepository
        );
    }

    @Test
    void findUserSubscriptions() throws ParseException {
        var anime = Anime.builder()
                .title("anime-1")
                .folderName("anime1")
                .build();
        var user = User.builder()
                .username("user")
                .password("pass")
                .build();

        var date1 = new SimpleDateFormat("dd-MM-yyyy").parse("13-03-2006");
        when(subscriptionForAnimeRepository.findByUserId(1))
                .thenReturn(List.of(
                        new SubscriptionForAnime(1, anime, user, date1)
                ));

        var album = Album.builder()
                .name("openings")
                .anime(anime)
                .build();
        var date2 = new SimpleDateFormat("dd-MM-yyyy").parse("07-07-2003");

        when(subscriptionForAlbumRepository.findByUserId(1))
                .thenReturn(List.of(
                        new SubscriptionForAlbum(2, album, user, date2)
                ));

        var subscriptions = contentSubscriptionService.findUserSubscriptions(1);

        assertThat(subscriptions.get(0)).extracting(
                        ContentSubscription::id,
                        ContentSubscription::user,
                        ContentSubscription::addedAt,
                        ContentSubscription::targetType)
                .containsExactly(1, user, date1, ANIME);

        assertThat(subscriptions.get(1)).extracting(
                        ContentSubscription::id,
                        ContentSubscription::user,
                        ContentSubscription::addedAt,
                        ContentSubscription::targetType)
                .containsExactly(2, user, date2, ALBUM);

        verify(subscriptionForAnimeRepository).findByUserId(1);
        verify(subscriptionForAlbumRepository).findByUserId(1);
    }
}