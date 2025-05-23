import {useContext, useEffect, useRef, useState} from "react";

import "./MusicPlayer.css"
import pauseButton from '@/shared/icons/pauseButton.png'
import rewindButton from '@/shared/icons/rewindButton.png'
import nextButton from '@/shared/icons/next.png'
import shuffleButton from '@/shared/icons/shuffleButton.png'
import shuffleActive from '@/shared/icons/shuffle-active.png'
import playButton from '@/shared/icons/playButton.png'
import repeatButton from '@/shared/icons/repeatButton.png'
import mediumSound from '@/shared/icons/icons8-средняя-громкость-100.png'
import littleSound from '@/shared/icons/icons8-низкая-громкость-100.png'
import loudSound from '@/shared/icons/icons8-громкий-звук-100.png'
import noSound from '@/shared/icons/icons8-нет-звука-100.png'
import {observer} from "mobx-react-lite";
import {isTablet} from 'react-device-detect';
import repeatButtonActive from '@/shared/icons/repeatButtonActive.png'
import {Context} from "@/main.tsx";
import {formatTime} from "@/shared/lib";
import {CurrentTrack} from "@/entities/soundtrack";
import {useLocation} from "react-router-dom";
import {SIGN_IN, SIGN_UP} from "@/shared/consts";


export const MusicPlayer = observer(() => {
    const audioRef = useRef<HTMLAudioElement>(null);
    const {musicStore} = useContext(Context)
    const location = useLocation()
    const [isShuffleActive, setIsShuffleActive] = useState(false);
    const [currentTime, setCurrentTime] = useState<number>(0);
    const [repeatStatus, setRepeatStatus] = useState<boolean>(false)
    const [duration, setDuration] = useState<number>(musicStore.currentTrack ? musicStore.currentTrack.duration : 0);
    useEffect(() => {
        if (isTablet) {
            musicStore.changeVolume(1)
        }
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
    const toggleShuffle = () => {
        setIsShuffleActive(!isShuffleActive)
    }
    const playPauseHandler = (): void => {
        musicStore.togglePlayPause()
    };
    const handlePlay = (): void => {
        musicStore.setIsPlaying(true)
    };

    const handlePause = (): void => {
        musicStore.setIsPlaying(false);
    };
    const handleVolumeChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const newVolume = parseFloat(event.target.value);
        musicStore.changeVolume(newVolume);

        const audioElement = audioRef.current;
        if (audioElement) {
            audioElement.volume = newVolume;
        }
    };
    const changeVolumeIcon = (volume: number): string => {
        if (volume === 0) {
            return noSound
        } else if (volume <= 0.3) {
            return littleSound
        } else if (volume <= 0.6) {
            return mediumSound
        } else {
            return loudSound
        }
    }
    const handleTimeUpdate = (event: React.ChangeEvent<HTMLAudioElement>): void => {
        setCurrentTime(event.target.currentTime);
    };
    const handleSeek = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const time: number = +event.target.value;
        if (audioRef.current) {
            audioRef.current.currentTime = time;
        }
        setCurrentTime(time);
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
    const toggleRepeat = (): void => {
        if (audioRef.current) {
            audioRef.current.loop = !repeatStatus;
            setRepeatStatus(audioRef.current.loop)
        }
    }
    const playNextTrack = (): void => {
        musicStore.nextTrack()
    };
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
    if (location.pathname !== SIGN_UP && location.pathname !== SIGN_IN) {
        return (
            <div className="music__player__wrapper">
                <CurrentTrack/>
                <div className={musicStore.currentTrack ? 'player' : 'player block'}>
                    <div className='player__buttons'>
                        <button onClick={toggleShuffle}><img src={isShuffleActive ? shuffleActive : shuffleButton}
                                                             alt="" style={{width: 24, height: 24}}/></button>
                        <button onClick={playPreviousTrack}><img src={rewindButton} alt=""
                                                                 style={{width: 27, height: 27}}/>
                        </button>
                        <button onClick={playPauseHandler}><img
                            src={musicStore.isPlaying ? pauseButton : playButton}
                            alt=""
                            style={{width: 40, height: 40}}/></button>
                        <button onClick={playNextTrack}><img src={nextButton} alt=""
                                                             style={{width: 27, height: 27}}/>
                        </button>
                        <button onClick={toggleRepeat}><img src={repeatStatus ? repeatButtonActive : repeatButton}
                                                            alt=""
                                                            style={{width: 27, height: 27}}/></button>
                    </div>
                    <div className="time__bar">
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
                        <div className="current__time">{formatTime(currentTime)}</div>
                        <div className="progress__bar">
                            <input
                                type="range"
                                min="0"
                                max={audioRef.current ? audioRef.current.duration : 0}
                                step="1"
                                value={currentTime}
                                onChange={handleSeek}
                            />
                        </div>
                        <div className="total__time">{formatTime(duration)}</div>
                    </div>
                </div>


                <div className={musicStore.currentTrack ? 'volume__bar' : 'volume__bar block'}>
                    <img className="volume__icon" src={changeVolumeIcon(musicStore.volume)} alt=""/>
                    <input
                        type="range"
                        min="0"
                        max="1"
                        step="0.01"
                        value={musicStore.volume}
                        onChange={handleVolumeChange}
                    />
                </div>
            </div>
        );
    }
})