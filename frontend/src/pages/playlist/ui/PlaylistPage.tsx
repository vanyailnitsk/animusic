import {useContext, useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {Playlist} from "@/entities/playlist";
import {COLLECTION, HOME_ROUTE} from "@/shared/consts";
import {Context} from "@/main.tsx";
import fav from '@/shared/assets/icons/icons8-избранное-500.png'
import {observer} from "mobx-react-lite";
import {SoundtrackList} from "@/widgets/soundtrack-list";
import {SoundtrackType} from "@/shared/types";
import styles from "./playlist-page.module.css";
import {getCollection, selectMusicState} from "@/entities/music";
import {useAppSelector} from "@/shared/lib/store";


export const PlaylistPage = observer(() => {
    const location = useLocation()
    const musicStore = useAppSelector(selectMusicState)
    const {userStore} = useContext(Context)
    const navigate = useNavigate()
    const [playlist, setPlaylist] = useState<Playlist | null>(null)
    useEffect(() => {
        if (location.pathname === COLLECTION) {
            getCollection()
                .then(playlist => {
                    setPlaylist(playlist)
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }, [musicStore.fav_tracks]);
    useEffect(() => {
        if(!userStore.isAuth){
            navigate(HOME_ROUTE)
        }
    }, [userStore.isAuth]);
    return (
        <div>
            {playlist && <div className={styles.playlist__page__header}>
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
                            <span style={{fontSize: 14}}>{`${playlist.soundtracks.length} tracks`}</span>
                        </div>
                    </div>
                </div>
            </div>}
            <div className={styles.playlist__page__bottom_rgb}></div>
            {playlist &&
                <div>
                    <SoundtrackList soundtracks={playlist.soundtracks} type={SoundtrackType.playlist}/>
                </div>

            }
        </div>
    );
});

