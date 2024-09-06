import styles from "./playlist-soundtrack.module.css";
import Pause from '@/shared/assets/icons/soundtrack-pause.png';
import Play from '@/shared/assets/icons/soundtrack-play.png';
import {CalculateDataOfAddition, formatTime} from "@/shared/lib";
import {IPlaylistSoundtrack} from "@/entities/soundtrack";
import {SaveTrack} from "@/features/collection";
import {useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "@/shared/lib/store";
import {
    isTrackEquals,
    selectMusicState,
    setIsPlaying,
    setPlaylist,
    setTrackIndex,
    togglePlayPause
} from "@/entities/music";

interface PlaylistSoundtrackProps {
    soundtrackData: IPlaylistSoundtrack;
    listening_queue: IPlaylistSoundtrack[];
    index: number;
}

export const PlaylistSoundtrack = ({soundtrackData, listening_queue, index}: PlaylistSoundtrackProps) => {
    const musicStore = useAppSelector(selectMusicState)
    const dispatch = useAppDispatch()
    const trackEquals = useAppSelector(state => isTrackEquals(state.music,soundtrackData.soundtrack))
    const navigate = useNavigate()
    const image = soundtrackData.soundtrack.image?.source || "images/track-img.jpeg"
    const playTrackHandler = () => {
        if (!trackEquals) {
            dispatch(setPlaylist(listening_queue));
            dispatch(setTrackIndex(index));
            dispatch(setIsPlaying(true));
        } else {
            dispatch(togglePlayPause());
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
        <div className={`${styles.playlist__soundtrack__wrapper} ${trackEquals? styles.playing : ""}`}
             onClick={playTrackHandler}>
            <button className={styles.soundtrack__toggle__play}>
                <span>{index + 1}</span>
                {trackEquals && musicStore.isPlaying ? (
                    <img src={Pause} alt="Pause"/>
                ) : (
                    <img src={Play} alt="Play"/>
                )}
            </button>
            <img src={image} alt="" className={styles.soundtrack__image}/>
            <div className={styles.title}>
                <span className={styles.anime__title} onClick={albumNavigate}>{soundtrackData.soundtrack.animeTitle}</span>
                <span className={styles.anime__name} onClick={animeNavigate}>{soundtrackData.soundtrack.anime.title}</span>
            </div>
            <p className={styles.original__title}>{soundtrackData.soundtrack.originalTitle}</p>
            <div className={styles.date_of_addition}>
                <span>{CalculateDataOfAddition(soundtrackData.addedAt)}</span>
            </div>
            <SaveTrack className={styles.soundtrack__saved} id={soundtrackData.soundtrack.id}/>
            <span className={styles.track__duration}>{formatTime(soundtrackData.soundtrack.duration)}</span>
        </div>
    );
};
