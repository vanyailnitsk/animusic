import {useContext, useEffect, useRef, useState} from "react";

import "./MusicPlayer.css"
import pauseButton from '../../icons/pauseButton.png'
import rewindButton from '../../icons/rewindButton.png'
import rollUp from '../../icons/rollUp.png'
import nextButton from '../../icons/next.png'
import shuffleButton from '../../icons/shuffleButton.png'
import shuffleActive from '../../icons/shuffle-active.png'
import playButton from '../../icons/playButton.png'
import repeatButton from '../../icons/repeatButton.png'
import addButton from '../../icons/addButton.png'
import mediumSound from '../../icons/icons8-средняя-громкость-100.png'
import littleSound from '../../icons/icons8-низкая-громкость-100.png'
// import loudSound from '../../icons/icons8-громкий-звук-100.png'
import noSound from '../../icons/icons8-нет-звука-100.png'
import {observer} from "mobx-react-lite";
import {Context} from "../../main.tsx";
import {formatTime} from "../../tools/FormatTime.ts";
import {isMobile, isTablet} from 'react-device-detect';
import repeatButtonActive from '../../icons/repeatButtonActive.png'
import {storageUrl} from "../../services/api/consts";
import CurrentTrack from "./CurrentTrack/CurrentTrack";


const MusicPlayer = observer(() => {
    const audioRef = useRef<HTMLAudioElement>(null);
    const {musicStore, userStore} = useContext(Context)
    const [isShuffleActive, setIsShuffleActive] = useState(false);
    const [currentTime, setCurrentTime] = useState<number>(0);
    const [repeatStatus, setRepeatStatus] = useState<boolean>(false)
    const [duration, setDuration] = useState<number>(musicStore.currentTrack ? musicStore.currentTrack.duration : 0);
    const [activePhonePlayer, setActivePhonePlayer] = useState<boolean>(false)
    useEffect(() => {
        if (isMobile) {
            musicStore.changeVolume(1);
        }
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
            return
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
    const handlePhoneMusicPlayer = (): void => {
        setActivePhonePlayer(!activePhonePlayer)
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
                {src: musicStore.currentTrack && storageUrl + (musicStore.currentTrack.image?.source || "images/track-img.jpeg"), sizes: '512x512', type: 'image/png'}
            ]
        });
    }
    if (isMobile && userStore.isAuth) {
        return (
            <div className={activePhonePlayer ? "music__player__wrapper active" : "music__player__wrapper"}
                 onClick={!activePhonePlayer ? handlePhoneMusicPlayer : undefined}>
                <img
                    src={musicStore.currentTrack && storageUrl + (musicStore.currentTrack.image?.source || "images/track-img.jpeg")}
                    alt=""
                    className={activePhonePlayer ? 'track__img active' : 'track__img'}/>
                <img src={addButton} alt="" className={activePhonePlayer ? 'add__track active' : 'add__track'}/>
                <img src={rollUp} alt="" className={activePhonePlayer ? 'roll__up active' : 'roll__up'}
                     onClick={handlePhoneMusicPlayer}/>
                <div className={activePhonePlayer ? "time__bar active" : "time__bar"}>
                    <audio ref={audioRef}
                           src={musicStore.currentTrack && storageUrl + musicStore.currentTrack.audioFile}
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
                {musicStore.currentTrack &&
                    <div className={activePhonePlayer ? 'track__name active' : 'track__name'}>
                        <span
                            className={musicStore.currentTrack.originalTitle.length > 20 ? "scrolling" : ""}>{musicStore.currentTrack.originalTitle}</span>
                        <span>{musicStore.currentTrack.animeTitle}</span>
                    </div>
                }
                <div className={activePhonePlayer ? 'player__buttons active' : 'player__buttons'}>
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
        );
    } else {
        if (userStore.isAuth) {
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
                                   src={musicStore.currentTrack && storageUrl + musicStore.currentTrack.audioFile}
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

    }
});

export default MusicPlayer;
