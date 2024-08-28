import styles from "./phone-music-player.module.css";
import {SaveTrack} from "@/features/collection";
import {FC, useContext, useEffect, useRef, useState} from "react";
import {Context} from "@/main.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import {Sheet} from "react-modal-sheet";
import {formatTime} from "@/shared/lib";
import shuffleButton from "@/shared/icons/shuffleButton.png";
import rewindButton from "@/shared/icons/rewindButton.png";
import pauseButton from "@/shared/icons/pauseButton.png";
import playButton from "@/shared/icons/playButton.png";
import nextButton from "@/shared/icons/next.png";
import repeatButtonActive from "@/shared/icons/repeatButtonActive.png";
import repeatButton from "@/shared/icons/repeatButton.png";
import {observer} from "mobx-react-lite";
import {SIGN_IN, SIGN_UP} from "@/shared/consts";
export const PhoneMusicPlayer = observer(() => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
    const location = useLocation()
    const [isOpen, setOpen] = useState(false);
    const audioRef = useRef<HTMLAudioElement>(null);
    const [currentTime, setCurrentTime] = useState<number>(0);
    const [duration, setDuration] = useState<number>(musicStore.currentTrack ? musicStore.currentTrack.duration : 0);
    const [repeatStatus, setRepeatStatus] = useState<boolean>(false)
    useEffect(() => {
        musicStore.changeVolume(1)
        if (audioRef.current) {
            if (audioRef.current) {
                audioRef.current.volume = musicStore.volume
            }
            if (musicStore.isPlaying) {
                audioRef.current.play()
            } else {
                audioRef.current.pause()
            }
        }
    }, [musicStore.isPlaying])
    useEffect(() => {
        const audioElement = audioRef.current;

        const onTimeUpdate = () => {
            if (audioElement) {
                setCurrentTime(audioElement.currentTime);
            }
            if (musicStore.currentTrack) {
                setDuration(musicStore.currentTrack.duration)
            }
        };
        if (audioElement) {
            audioElement.addEventListener("timeupdate", onTimeUpdate);
            return () => {
                if (audioElement) {
                    audioElement.removeEventListener("timeupdate", onTimeUpdate);
                }
            };
        }

    }, []);
    const handleTimeUpdate = (event: React.ChangeEvent<HTMLAudioElement>): void => {
        setCurrentTime(event.target.currentTime);
    };
    const playNextTrack = (): void => {
        musicStore.nextTrack()
    };
    const playPreviousTrack = (): void => {
        if (musicStore.trackIndex > 0 && audioRef.current && audioRef.current.currentTime < 4) {
            musicStore.previousTrack()
        } else {
            if (audioRef.current) {
                audioRef.current.currentTime = 0;
                setCurrentTime(0)
            }
        }
    };
    const handlePlay = (): void => {
        musicStore.setIsPlaying(true)
    };

    const handlePause = (): void => {
        musicStore.setIsPlaying(false);
    };
    const playPauseHandler = (): void => {
        musicStore.togglePlayPause()
    };



    const handleSeek = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const time: number = +event.target.value;
        if (audioRef.current) {
            audioRef.current.currentTime = time;
        }
        setCurrentTime(time);
    };

    const toggleRepeat = (): void => {
        if (audioRef.current) {
            audioRef.current.loop = !repeatStatus;
            setRepeatStatus(audioRef.current.loop)
        }
    }
    const handleAlbumNavigate = (e) => {
        e.stopPropagation()
        navigate(`/album/${musicStore.currentTrack?.album.id}`)
    }
    navigator.mediaSession.setActionHandler("nexttrack", (): void => {
        playNextTrack()
    });
    navigator.mediaSession.setActionHandler("previoustrack", (): void => {
        playPreviousTrack()
    });
    if (musicStore.currentTrack) {
        navigator.mediaSession.metadata = new MediaMetadata({
            title: musicStore.currentTrack.originalTitle,
            artist: musicStore.currentTrack.animeTitle,
            artwork: [
                {
                    src: musicStore.currentTrack && musicStore.currentTrack.image?.source || "images/track-img.jpeg",
                    sizes: '512x512',
                    type: 'image/png'
                }
            ]
        });
    }
    if (location.pathname !== SIGN_UP && location.pathname !== SIGN_IN){
        return (
            <div className={musicStore.currentTrack ? styles.phone__music__player__wrapper : styles.hidden}
                 onClick={() => setOpen(true)}>
                <div className={styles.phone__music__player__content}>
                    <audio ref={audioRef}
                           src={musicStore.currentTrack && musicStore.currentTrack.audioFile}
                           autoPlay
                           onEnded={playNextTrack}
                           onTimeUpdate={handleTimeUpdate}
                           onPlay={handlePlay}
                           onPause={handlePause}
                           preload="auto"
                    >
                    </audio>
                    <img
                        src={musicStore.currentTrack && musicStore.currentTrack.image?.source || "images/track-img.jpeg"}
                        alt=""
                        className={styles.track__img}/>
                    {musicStore.currentTrack &&
                        <div className={styles.track__name}>
                            <span
                                onClick={handleAlbumNavigate}
                                className={musicStore.currentTrack.originalTitle.length > 20 ? styles.scrolling : ""}>{musicStore.currentTrack.originalTitle}</span>
                            <span>{musicStore.currentTrack.animeTitle}</span>
                        </div>
                    }
                    {musicStore.currentTrack && <SaveTrack className={styles.save__track} id={musicStore.currentTrack.id}
                                                           saved={musicStore.isSaved(musicStore.currentTrack.id)}/>}
                </div>
                <Sheet isOpen={isOpen} onClose={() => setOpen(false)}>
                    <Sheet.Container>
                        <Sheet.Header style={{backgroundColor: '#060606'}}/>
                        <Sheet.Content>
                            <ActiveMusicPlayer
                                currentTime={currentTime}
                                duration={duration}
                                audioRef={audioRef}
                                handleSeek={handleSeek}
                                playNextTrack={playNextTrack}
                                playPauseHandler={playPauseHandler}
                                playPreviousTrack={playPreviousTrack}
                                toggleRepeat={toggleRepeat}
                                repeatStatus={repeatStatus}
                            />
                        </Sheet.Content>
                    </Sheet.Container>
                    <Sheet.Backdrop/>
                </Sheet>
            </div>
        );
    }
});
interface ActivePlayerProps{
    currentTime:number;
    duration:number;
    audioRef:React.MutableRefObject<HTMLAudioElement>;
    playPreviousTrack:() => void
    playPauseHandler:() => void
    playNextTrack:() => void
    toggleRepeat:() => void
    repeatStatus:boolean;
    handleSeek:(event: React.ChangeEvent<HTMLInputElement>) => void;
}
const ActiveMusicPlayer:FC<ActivePlayerProps> = observer((props) => {
    const {currentTime,duration,audioRef,playNextTrack,playPreviousTrack,playPauseHandler,toggleRepeat,repeatStatus,handleSeek} = props
    const {musicStore} = useContext(Context)
    return (
        <div className={styles.phone__music__player__wrapper__active}>
            <img
                src={musicStore.currentTrack && musicStore.currentTrack.image?.source || "images/track-img.jpeg"}
                alt=""
                className={styles.track__img__active}
            />
            {musicStore.currentTrack &&
                <div className={styles.track__content}>
                    <div className={styles.track__name__active}>
                        <p>{musicStore.currentTrack.originalTitle}</p>
                        <p>{musicStore.currentTrack.animeTitle}</p>
                    </div>
                    <SaveTrack className={styles.save__track__active}
                               id={musicStore.currentTrack.id}
                               saved={musicStore.isSaved(musicStore.currentTrack.id)}
                    />
                </div>
            }
            <div className={styles.time__bar}>
                <div className={styles.current__time}>{formatTime(currentTime)}</div>
                <div className={styles.progress__bar}>
                    <input
                        type="range"
                        min="0"
                        max={audioRef.current ? audioRef.current.duration : 0}
                        step="1"
                        value={currentTime}
                        onChange={handleSeek}
                    />
                </div>
                <div className={styles.total__time}>{formatTime(duration)}</div>
            </div>
            <div className={styles.player__buttons}>
                <button><img src={shuffleButton} alt="" style={{width: 34, height: 34}}/></button>
                <button onClick={playPreviousTrack}><img src={rewindButton} alt="" style={{width: 37, height: 37}}/>
                </button>
                <button onClick={playPauseHandler}><img src={musicStore.isPlaying ? pauseButton : playButton} alt=""
                                                        style={{width: 50, height: 50}}/></button>
                <button onClick={playNextTrack}><img src={nextButton} alt="" style={{width: 37, height: 37}}/>
                </button>
                <button onClick={toggleRepeat}><img src={repeatStatus ? repeatButtonActive : repeatButton} alt=""
                                                    style={{width: 37, height: 37}}/></button>
            </div>
        </div>
    )
})

