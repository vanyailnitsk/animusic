import {useContext, useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {Playlist} from "@/entities/playlist";
import {COLLECTION} from "@/shared/consts";
import {Context} from "@/main.tsx";
import fav from '@/shared/icons/icons8-избранное-500.png'
import {MusicService} from "@/shared/services";
import {observer} from "mobx-react-lite";
import {SoundtrackList} from "@/widgets/soundtrack-list";
import {SoundtrackType} from "@/shared/types";
import styles from "./playlist-page.module.css";


export const PlaylistPage = observer(() => {
    const location = useLocation()
    const {musicStore} = useContext(Context)
    const [playlist, setPlaylist] = useState<Playlist | null>(null)
    useEffect(() => {
        if (location.pathname === COLLECTION) {
            MusicService.getCollection()
                .then(response => {
                    setPlaylist(response.data)
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }, [musicStore.fav_tracks]);
    return (
        <div>
            <div className={styles.playlist__page__header}>
                <div className={styles.playlist__page__header__content}>
                    <div className={styles.playlist__image}>
                        <img
                            src={fav} alt="Banner"
                        />
                    </div>
                    <div className={styles.playlist__main__info}>
                        <span className={styles.type__content}>Playlist</span>
                        <div className={styles.playlist__name}>Favorites</div>
                        <div>
                            <span style={{fontSize: 14}}>{`${playlist?.soundtracks.length} tracks`}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div className={styles.playlist__page__bottom_rgb}></div>
            {playlist &&
                <div>
                    <SoundtrackList soundtracks={playlist.soundtracks} type={SoundtrackType.playlist}/>
                </div>

            }
        </div>
    );
});

