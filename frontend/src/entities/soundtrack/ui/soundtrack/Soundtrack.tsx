import "./Soundtrack.css";
import Pause from '@/shared/assets/icons/soundtrack-pause.png';
import Play from '@/shared/assets/icons/soundtrack-play.png';
import {observer} from "mobx-react-lite";
import {formatTime} from "@/shared/lib";
import {ISoundtrack, SoundtrackData} from "@/entities/soundtrack";
import {SaveTrack} from "@/features/collection";
import {useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "@/shared/lib/store";
import {
    isTrackEquals, isTrackSaved,
    selectMusicState,
    setIsPlaying,
    setPlaylist,
    setTrackIndex,
    togglePlayPause,
} from "@/entities/music";

interface SoundtrackProps {
    soundtrackData: SoundtrackData;
    listening_queue: ISoundtrack[];
    index: number;
}

export const Soundtrack = observer(({soundtrackData, listening_queue, index}: SoundtrackProps) => {
    const musicStore = useAppSelector(selectMusicState)
    const isSaved = useAppSelector(state => isTrackSaved(musicStore,soundtrackData.id))
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const trackEquals = useAppSelector(state => isTrackEquals(state.music,soundtrackData))
    const image =soundtrackData.image?.source || "images/track-img.jpeg"

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
        navigate(`/anime/${soundtrackData.anime.id}`)
    }
    return (
        <div className={`soundtrack__container ${trackEquals ? "playing" : ""}`}
             onClick={playTrackHandler}>
            <button className="soundtrack__toggle__play">
                <span>{index + 1}</span>
                {trackEquals && musicStore.isPlaying ? (
                    <img src={Pause} alt="Pause"/>
                ) : (
                    <img src={Play} alt="Play"/>
                )}
            </button>
            <img src={image} alt="" className="soundtrack__image"/>
            <div className='title'>
                <span className='anime__title'>{soundtrackData.animeTitle}</span>
                <span className='anime_name' onClick={animeNavigate}>{soundtrackData.anime.title}</span>
            </div>
            <p className="original__title">{soundtrackData.originalTitle}</p>
            <SaveTrack className={isSaved? "soundtrack__saved" : "soundtrack__add"} id={soundtrackData.id}/>
            <span className='track__duration'>{formatTime(soundtrackData.duration)}</span>
        </div>
    );
});
