import {useContext} from 'react';
import styles from './current-track.module.css'
import {useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {observer} from "mobx-react-lite";
import {SaveTrack} from "@/features/collection";

export const CurrentTrack = observer(() => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
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
            <SaveTrack className={styles.save__track} id={musicStore.currentTrack?.id} saved={musicStore.currentTrack && musicStore.isSaved(musicStore.currentTrack.id)}/>
        </div>
    );
});

