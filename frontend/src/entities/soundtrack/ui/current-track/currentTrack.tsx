import {MouseEventHandler, useContext} from 'react';
import addButton from "@/shared/icons/follow.png";
import savedTrack from '@/shared/icons/saved-track.png'
import styles from './current-track.module.css'
import {useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {observer} from "mobx-react-lite";

export const CurrentTrack = observer(() => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
    const handleSavedTrack: MouseEventHandler<HTMLButtonElement> = async (e) => {
        e.stopPropagation();
        if(musicStore.currentTrack){
            const trackId = musicStore.currentTrack.id;
            if (trackId) {
                if (musicStore.isSaved(trackId)) {
                    await musicStore.removeFromFavorites(trackId);
                } else {
                    await musicStore.addToFavorite(trackId);

                }
            }
        }
    };
    return (
        <div className={musicStore.currentTrack ? styles.current__track : styles.hidden}>
            <img
                src={musicStore.currentTrack && musicStore.currentTrack.image?.source || "images/track-img.jpeg"}
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
            <button className={styles.save__track} onClick={handleSavedTrack}>
                {musicStore.currentTrack && (musicStore.isSaved(musicStore.currentTrack.id)? <img src={savedTrack} alt="" />: <img src={addButton} alt="" /> )}
            </button>
        </div>
    );
});

