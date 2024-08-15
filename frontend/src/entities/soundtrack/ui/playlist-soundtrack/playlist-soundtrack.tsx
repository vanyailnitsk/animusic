import {useContext} from "react";
import styles from "./playlist-soundtrack.module.css";
import Pause from '@/shared/icons/soundtrack-pause.png';
import Play from '@/shared/icons/soundtrack-play.png';
import {observer} from "mobx-react-lite";
import {Context} from "@/main.tsx";
import {CalculateDataOfAddition, formatTime} from "@/shared/lib";
import {IPlaylistSoundtrack} from "@/entities/soundtrack";
import {SaveTrack} from "@/features/collection";
import {useNavigate} from "react-router-dom";

interface PlaylistSoundtrackProps {
    soundtrackData: IPlaylistSoundtrack;
    listening_queue: IPlaylistSoundtrack[];
    index: number;
}

export const PlaylistSoundtrack = observer(({soundtrackData, listening_queue, index}: PlaylistSoundtrackProps) => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
    const image = soundtrackData.soundtrack.image?.source || "images/track-img.jpeg"
    const playTrackHandler = () => {
        if (!musicStore.trackEquals(soundtrackData.soundtrack)) {
            musicStore.setPlaylist(listening_queue);
            musicStore.setTrackIndex(index);
            musicStore.setIsPlaying(true);
        } else {
            musicStore.togglePlayPause();
        }
    };
    const animeNavigate = (e:any) => {
        e.stopPropagation()
        navigate(`/anime/${soundtrackData.soundtrack.anime.id}`)
    }
    const albumNavigate = (e:any) => {
        e.stopPropagation()
        navigate(`/album/${soundtrackData.soundtrack.album.id}`)
    }
    return (
        <div className={`${styles.playlist__soundtrack__wrapper} ${musicStore.trackEquals(soundtrackData.soundtrack) ? styles.playing : ""}`}
             onClick={playTrackHandler}>
            <button className={styles.soundtrack__toggle__play}>
                <span>{index + 1}</span>
                {musicStore.trackEquals(soundtrackData.soundtrack) && musicStore.isPlaying ? (
                    <img src={Pause} alt="Pause"/>
                ) : (
                    <img src={Play} alt="Play"/>
                )}
            </button>
            <img src={image} alt="" className={styles.soundtrack__image}/>
            <div className={styles.title}>
                <h3 className={styles.anime__title} onClick={albumNavigate}>{soundtrackData.soundtrack.animeTitle}</h3>
                <span className={styles.anime_name} onClick={animeNavigate}>{soundtrackData.soundtrack.anime.title}</span>
            </div>
            <p className={styles.original__title}>{soundtrackData.soundtrack.originalTitle}</p>
            <div className={styles.date_of_addition}>
                <span>{CalculateDataOfAddition(soundtrackData.addedAt)}</span>
            </div>
            <SaveTrack className={styles.soundtrack__saved} id={soundtrackData.soundtrack.id} saved={musicStore.isSaved(soundtrackData.soundtrack.id)}/>
            <span className={styles.track__duration}>{formatTime(soundtrackData.soundtrack.duration)}</span>
        </div>
    );
});
