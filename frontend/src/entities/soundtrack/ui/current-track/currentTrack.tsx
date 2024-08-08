import {MouseEventHandler, useContext} from 'react';
import addButton from "@/shared/icons/addButton.png";
import styles from './current-track.module.css'
import {useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {storageUrl} from "@/shared/api";

export const CurrentTrack = () => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
    const addTrack: MouseEventHandler<HTMLImageElement> = (e) => {
        e.stopPropagation();
        if (musicStore.currentTrack?.id) {
            musicStore.addToFavorite(musicStore.currentTrack.id);
        }
    };
    return (
        <div className={musicStore.currentTrack ? styles.current__track : styles.hidden}>
            <img
                src={musicStore.currentTrack && storageUrl + (musicStore.currentTrack.image?.source || "images/track-img.jpeg")}
                alt=""
                className={styles.track__img}/>
            {musicStore.currentTrack &&
                <div className={styles.track__name}>
                            <span
                                onClick={() => navigate(`/album/${musicStore.currentTrack?.album.id}`)}
                                className={musicStore.currentTrack.originalTitle.length > 20 ? styles.scrolling : ""}>{musicStore.currentTrack.originalTitle}</span>
                    <span>{musicStore.currentTrack.animeTitle}</span>
                </div>
            }
            <img src={addButton} alt="" className={styles.add__track} onClick={addTrack}/>
        </div>
    );
};

